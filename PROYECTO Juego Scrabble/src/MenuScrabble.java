import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuScrabble {
    private final Scanner scanner;
    private boolean salir;
    private Jugador[] jugadores;
    private Map<String, Juego> partidasPendientes;

    /**
     * Constructor de la clase MenuScrabble.
     * <p>
     * Inicializa el scanner, establece el estado de salida en falso y solicita el ingreso de dos jugadores.
     */
    public MenuScrabble() {
        scanner = new Scanner(System.in);
        salir = false;
        partidasPendientes = JsonUtil.cargarTodasLasPartidasPendientes(); // Carga todas las partidas pendientes al iniciar
        try {
            System.out.println("Para forzar el cierre del programa escriba \"salirDelPrograma\" exactamente como se muestra.");
            System.out.println("Asegúrese de colocar bien la ruta de donde vaya a extraer los usuarios.json");
            Jugador jugador1 = ingresarUsuario(1);
            Jugador jugador2 = ingresarUsuario(2);
            this.jugadores = new Jugador[]{jugador1, jugador2};
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(130);
        }
    }

    /**
     * Muestra el menú principal de opciones.
     *
     * @return una cadena que representa el menú principal.
     */
    private String menuPrincipal() {
        return """
        =======================================
                  Menú de Opciones
        =======================================
        [1]- Jugar una partida nueva
        [2]- Continuar la partida anterior
        [3]- Ver Score de los jugadores
        [0]- Salir
        =======================================
        Selecciona una opción:\s
        """;
    }

    /**
     * Solicita al usuario ingresar el nombre de usuario para un jugador.
     * <p>
     * Verifica si el usuario ya existe en el archivo JSON y lo carga, o solicita un nuevo nombre de usuario.
     *
     * @param tipoJugador el número del jugador (1 o 2).
     * @return el jugador cargado o creado.
     * @throws RuntimeException si se ingresa "salirDelPrograma" para forzar el cierre.
     */
    private Jugador ingresarUsuario(int tipoJugador) {
        while (true) {
            System.out.print("Ingrese el usuario del jugador " + tipoJugador + ": ");
            String nombre = scanner.next();
            if (nombre.equals("salirDelPrograma")) {
                throw new RuntimeException("Saliendo del programa, registrese para poder jugar!!!");
            }
            nombre = nombre.toLowerCase();

            // Cargar usuarios desde el archivo JSON
            List<Jugador> usuarios = JsonUtil.cargarUsuarios();

            // Verificar si el usuario ya existe
            for (Jugador usuario : usuarios) {
                if (usuario.getAlias().equals(nombre)) {
                    System.out.println("Cargando usuario existente: " + nombre);
                    return usuario;
                }
            }

            // Si el usuario no se encuentra, solicitar nuevamente
            System.out.println("\nEl usuario que ingresó: " + nombre + " no es un usuario registrado. Por favor, ingrese un usuario registrado.");
        }
    }

    /**
     * Muestra el menú de opciones y gestiona la selección del usuario.
     */
    public void mostrarMenu() {
        while (!salir) {
            limpiarPantalla();
            System.out.println(menuPrincipal());
            String opcion = scanner.next();

            switch (opcion) {
                case "1":
                    jugarNuevaPartida();
                    break;
                case "2":
                    continuarPartidaAnterior();
                    break;
                case "3":
                    verScoreJugadores();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo del juego...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        }
    }

    /**
     * Inicia una nueva partida de Scrabble.
     */
    private void jugarNuevaPartida() {
        Juego nuevoJuego = new Juego(jugadores);
        nuevoJuego.iniciarNuevaPartida();
    }

    /**
     * Continúa una partida anterior guardada.
     */
    private void continuarPartidaAnterior() {
        System.out.println("Continuando la partida anterior...");
        Juego continuarJuego = new Juego(jugadores);
        continuarJuego = JsonUtil.cargarPartidaPendiente(continuarJuego.getClaveJugadores());
        try {
            if (!continuarJuego.isPartidaTerminada()) {
                continuarJuego.reInicializarScanner();
                continuarJuego.continuarPartida();

                if (continuarJuego.isPartidaTerminada()) {
                    JsonUtil.guardarPartidaTerminada(continuarJuego);
                } else {
                    JsonUtil.guardarPartidaPendiente(continuarJuego);
                }
            } else {
                System.out.println("Estos jugadores no tienen partidas pendientes, la Partida fue Terminada, porfavor inicien una nueva partida");
            }
        } catch (NullPointerException e) {
            System.out.println("Estos jugadores no tienen una partida guardada o su partida fue eliminada, porfavor inicien una nueva partida");
        }
    }

    /**
     * Muestra los puntajes de los jugadores en formato de tabla estilo Excel.
     */
    private void verScoreJugadores() {
        mostrarSubmenuScore();
    }

    /**
     * Muestra los puntajes de los jugadores en una tabla con formato estilo Excel.
     * <p>
     * Este método imprime una tabla que contiene los puntajes totales de los jugadores,
     * la cantidad de palabras colocadas y el tiempo jugado total en horas, minutos y segundos.
     */
    public void mostrarPuntajesEnTablaEstiloExcel() {
        System.out.println("\n+-----------------+------------+-------------------+------------+------------+------------+");
        System.out.printf("| %-15s | %-10s | %-17s | %-10s | %-10s | %-10s |\n", "Jugador", "Puntaje Total", "Cantidad de Palabras", "Horas", "Minutos", "Segundos");
        System.out.println("+-----------------+------------+-------------------+------------+------------+------------+");

        for (Jugador jugador : jugadores) {
            System.out.printf("| %-15s | %-10d | %-17d | %-10d | %-10d | %-10d |\n",
                    jugador.getAlias(),
                    jugador.getScoreTotal(),
                    jugador.getCantidadDePalabras(),
                    jugador.getHorasJugadas(),
                    jugador.getMinutosJugados(),
                    jugador.getSegundosJugados());
        }

        System.out.println("+-----------------+------------+-------------------+------------+------------+------------+");
    }



    /**
     * Muestra un submenu para los scores.
     * <p>
     */
    private void mostrarSubmenuScore() {
        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (!opcion.equals("3")) {
            System.out.println("Submenú de Puntajes:");
            System.out.println("1. Ver Puntuaciones");
            System.out.println("2. Ver Partidas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    mostrarPuntajesEnTablaEstiloExcel();
                    break;
                case "2":
                    mostrarPartidasFiltradasEnTabla();
                    break;
                case "3":
                    System.out.println("Saliendo del submenú...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }


    /**
     * Muestra una tabla de partidas filtradas en curso y terminadas.
     * <p>
     * Este método imprime una tabla que contiene información sobre las partidas filtradas.
     * La tabla incluye los jugadores, sus puntajes en juego, la cantidad de palabras colocadas,
     * el tiempo jugado in-game en formato (h:m:s) y el estado de la partida (terminada o en curso).
     */
    /**
     * Muestra una tabla de partidas filtradas en curso y terminadas.
     * <p>
     * Este método imprime una tabla que contiene información sobre las partidas filtradas.
     * La tabla incluye los jugadores, sus puntajes en juego, la cantidad de palabras colocadas,
     * el tiempo jugado in-game en formato (h:m:s) y el estado de la partida (terminada o en curso).
     */
    /**
     * Muestra una tabla de partidas filtradas en curso y terminadas.
     * <p>
     * Este método imprime una tabla que contiene información sobre las partidas filtradas.
     * La tabla incluye los jugadores, sus puntajes en juego, la cantidad de palabras colocadas,
     * el tiempo jugado in-game en formato (h:m:s) y el estado de la partida (terminada o en curso).
     */
    public void mostrarPartidasFiltradasEnTabla() {
        System.out.println("\n+-------------------+---------------------------+-----------------------+--------------------------+--------------+");
        System.out.printf("| %-17s | %-25s | %-23s | %-25s | %-12s |\n", "Jugadores", "Score In-Game", "Cantidad de Palabras", "Tiempo In-Game (h:m:s)", "Estado");
        System.out.println("+-------------------+---------------------------+-----------------------+--------------------------+--------------+");

        for (Map.Entry<String, Juego> entry : partidasPendientes.entrySet()) {
            String claveJugadores = entry.getKey();
            Juego juego = entry.getValue();

            boolean contieneJugador = false;
            for (Jugador jugador : jugadores) {
                if (claveJugadores.contains(jugador.getAlias())) {
                    contieneJugador = true;
                    break;
                }
            }

            if (contieneJugador) {
                StringBuilder jugadoresNombres = new StringBuilder();
                StringBuilder scoresInGame = new StringBuilder();
                StringBuilder cantidadPalabras = new StringBuilder();
                StringBuilder tiemposJugadosInGame = new StringBuilder();

                for (Jugador jugador : juego.getJugadores()) {
                    jugadoresNombres.append(jugador.getAlias()).append(" ");
                    scoresInGame.append(jugador.getScoreInGame()).append(" ");
                    cantidadPalabras.append(jugador.getCantidadPalabrasColocadas()).append(" ");
                }
                tiemposJugadosInGame.append(juego.getJugadores()[0].getHorasJugadasInGame()).append("h ")
                        .append(juego.getJugadores()[0].getMinutosJugadosInGame()).append("m ")
                        .append(juego.getJugadores()[0].getSegundosJugadosInGame()).append("s ");

                System.out.printf("| %-17s | %-25s | %-23s | %-25s | %-12s |\n",
                        jugadoresNombres.toString().trim(),
                        scoresInGame.toString().trim(),
                        cantidadPalabras.toString().trim(),
                        tiemposJugadosInGame.toString().trim(),
                        juego.isPartidaTerminada() ? "Terminada" : "En curso");
            }
        }

        System.out.println("+-------------------+---------------------------+-----------------------+--------------------------+--------------+");
    }


    /**
     * Limpia la pantalla de la consola.
     */
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
