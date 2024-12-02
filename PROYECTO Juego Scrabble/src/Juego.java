import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Juego {
    private int turnoActual;
    private Jugador jugadorActual;
    private boolean primeraJugada;
    private boolean primeraFichaPuesta;
    private boolean jugadaCompleta;
    private boolean turnoPasado;
    private boolean palabraCancelada;
    private ArrayList<int[]> indiceFichasPuestas;
    private final BolsaFichas bolsaFichas;
    private Controlador controlador;
    private Tablero tablero;
    private Tablero tableroAuxiliar;
    private final Jugador[] jugadores;
    private transient Scanner scanner = new Scanner(System.in);
    private final PalabraExtractor palabraExtractor = new PalabraExtractor();
    private boolean partidaTerminada = false;
    private transient boolean salir = false;
    private long tiempoInicio;
    private long tiempoAcumulado;


    /**
     * Reinicializa el scanner para leer entrada del usuario.
     */
    public void reInicializarScanner(){
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor de la clase Juego.
     *
     * @param jugadores Array de objetos Jugador que participan en el juego.
     */
    public Juego(Jugador[] jugadores) {
        this.jugadores = jugadores;
        this.tablero = new Tablero();
        this.tableroAuxiliar = new Tablero(tablero);
        this.turnoActual = 0;
        this.jugadorActual = null;
        this.primeraJugada = true;
        this.primeraFichaPuesta = false;
        this.jugadaCompleta = false;
        this.turnoPasado = false;
        this.palabraCancelada = false;
        this.indiceFichasPuestas = new ArrayList<>();
        this.bolsaFichas = new BolsaFichas();
        this.controlador = new Controlador(tableroAuxiliar);
    }

    /**
     *  Inicia una nueva partida de Scrabble.
     * <p> Este método inicializa el tiempo de inicio de la partida, resetea el tiempo acumulado,
     * y * gestiona el flujo de la partida hasta que termine. Al final, actualiza los datos de los jugadores.
     * */
    public void iniciarNuevaPartida() {
        tiempoInicio = Instant.now().getEpochSecond();
        tiempoAcumulado = 0;
        inicializarNuevoJuego();

        while (partidaTerminada(jugadores[turnoActual], bolsaFichas) && !salir) {
            gestionarTurno();
        }

        actualizarTiempoJugado();

        for (Jugador jugador : jugadores) {
            actualizarTiempoInGame(jugador, tiempoAcumulado);
            actualizarTotalesJugador(jugador);
            actualizarTiempoTotal(jugador);
            actualizarDatosUsuario(jugador);
        }

        if (!salir) {
            mostrarResultadoFinal();
        }else {
            JsonUtil.guardarPartidaPendiente(this);
        }
    }

    /**
     * Continúa una partida guardada anteriormente.
     * <p>
     * Este método restablece el tiempo de inicio de la partida y gestiona el flujo de la partida
     * hasta que termine. Al final, actualiza los datos de los jugadores.
     */
    public void continuarPartida() {
        tiempoInicio = Instant.now().getEpochSecond();
        inicializarJuegoAnterior();
        while (partidaTerminada(jugadores[turnoActual], bolsaFichas) && !salir) {
            gestionarTurno();
        }

        actualizarTiempoJugado();
        for (Jugador jugador : jugadores) {
            actualizarTiempoInGame(jugador, tiempoAcumulado);
        }
        for (Jugador jugador : jugadores) {
            actualizarTotalesJugador(jugador);
            actualizarTiempoInGame(jugador, tiempoAcumulado);
            actualizarTiempoTotal(jugador);
            actualizarDatosUsuario(jugador);
        }

        if (!salir) {
            mostrarResultadoFinal();
        }else {
            JsonUtil.guardarPartidaPendiente(this);
        }
    }

    /**
     * Inicializa el estado del juego anterior.
     */
    private void inicializarJuegoAnterior() {
        salir = false;
        jugadorActual = jugadores[turnoActual];
        indiceFichasPuestas = new ArrayList<>();

        System.out.println("¡Continua la partida anterior!");
        System.out.println("Le toca al jugador: " + jugadorActual.getAlias());
    }

    /**
     * Inicializa el estado de un nuevo juego.
     */
    private void inicializarNuevoJuego() {
        turnoActual = new Random().nextInt(jugadores.length);
        jugadorActual = jugadores[turnoActual];
        primeraJugada = true;
        primeraFichaPuesta = false;
        jugadaCompleta = false;
        turnoPasado = false;
        palabraCancelada = false;
        indiceFichasPuestas = new ArrayList<>();

        System.out.println("¡Nueva partida iniciada!");
        System.out.println("Empieza el jugador: " + jugadorActual.getAlias());
    }

    /**
     * Gestiona el turno actual de un jugador.
     */
    private void gestionarTurno() {
        controlador = new Controlador(tableroAuxiliar);
        rellenarFichasJugadores(jugadores, bolsaFichas);
        jugadorActual = jugadores[turnoActual];
        Ficha[] atrilCopia = jugadorActual.clonarFichas();
        jugadaCompleta = false;
        turnoPasado = false;
        palabraCancelada = false;
        indiceFichasPuestas = new ArrayList<>();
        salir = false;
        JsonUtil.guardarPartidaPendiente(this);

        System.out.println("\nTurno de: " + jugadorActual.getAlias());
        System.out.println("Puntos: " + jugadorActual.getScore());

        if (primeraJugada) {
            manejarPrimeraJugada();
        } else {
            manejarJugadaRegular();
        }

        manejarFinTurno(atrilCopia);
    }
    /**
     * Maneja la primera jugada del juego.
     * <p>
     * Muestra el tablero inicial, instrucciones para el usuario y gestiona la colocación de la primera ficha en el centro del tablero.
     */
    private void manejarPrimeraJugada() {
        tablero.mostrarTablero();
        System.out.println("Debe colocar la primera Ficha en el centro del tablero.");
        System.out.println("\u001B[33m" + "Para regresar al menu principal debe poner primero la ficha central" + "\u001B[0m");
        System.out.println("Utilice las teclas (W A S D) para moverse en el tablero, debe presionar ENTER después de usar cada letra.");
        System.out.println("Para colocar una Ficha presione la letra P y luego ENTER.");
        mostrarFichasEIndices(jugadorActual);

        indiceFichasPuestas.add(ponerPrimeraFicha(jugadorActual));
        tableroAuxiliar.mostrarTablero();
        mostrarFichasEIndices(jugadorActual);

        if (indiceFichasPuestas.getFirst() != null) {
            primeraFichaPuesta = true;
        }

        gestionarEntradaUsuario();
    }

    /**
     * Maneja una jugada regular del juego.
     * <p>
     * Muestra el tablero y las fichas del jugador actual y gestiona la entrada del usuario.
     */
    private void manejarJugadaRegular() {
        tablero.mostrarTablero();
        mostrarFichasEIndices(jugadorActual);
        gestionarEntradaUsuario();
    }

    /**
     * Gestiona la entrada del usuario durante el turno.
     * <p>
     * Permite al usuario colocar fichas, verificar palabras, cancelar palabras, pasar turno o salir del juego.
     */
    private void gestionarEntradaUsuario() {
        while (!jugadaCompleta && primeraFichaPuesta) {
            System.out.println("Presione P y luego Enter para poner otra Ficha.");
            System.out.println("Presione L para verificar su palabra.");
            System.out.println("Presione C para CANCELAR su palabra.");

            if (!primeraJugada) {
                System.out.println("Presione 9 para pasar su turno");
            }
            System.out.println("Escriba \u001B[31mSALIR\u001B[0m para salir y guardar. Regresara al menu principal");

            String option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "l":
                    jugadaCompleta = true;
                    break;
                case "9":
                    if (!primeraJugada) {
                        if(confirmar("Estas seguro de querer pasar turno? Y/N","Usted NO ha saltado su turno" )){
                            turnoPasado = true;
                            jugadaCompleta = true;
                        }
                    }
                    break;
                case "c":
                    jugadaCompleta = true;
                    palabraCancelada = true;
                    break;
                case "salir":
                    if (confirmar("Estas seguro de querer Salir y Guardar? Y/N", "Usted NO ha salido, la partida se guarda automaticamente cada palabra" )) {
                        salir = true;
                        jugadaCompleta = true;
                    }
                    break;
                case"p":
                    System.out.println("Utilice (W A S D) para seleccionar la posición donde quiere poner la ficha.");
                    System.out.println("La ficha debe estar adyacente a otra o sera una jugada invalida.");
                    System.out.println("Presione P y luego 9 para cancelar");

                    indiceFichasPuestas.add(ponerFicha(jugadorActual));
                    tableroAuxiliar.mostrarTablero();
                    mostrarFichasEIndices(jugadorActual);
                    break;
                default:
                    System.out.println("Entrada invalida, esperando otra entrada.");
            }
        }
    }

    /**
     * Solicita al usuario confirmar una acción.
     *
     * @param mensaje1 Mensaje de confirmación.
     * @param mensaje2 Mensaje a mostrar si la confirmación es negativa.
     * @return true si el usuario confirma, false en caso contrario.
     */
    private boolean confirmar(String mensaje1, String mensaje2) {
        System.out.println(mensaje1);
        while (true) {
            String option2 = scanner.nextLine().toLowerCase();
            if (option2.equals("y")) {
                jugadaCompleta = true;
                return true;
            } else if (option2.equals("n")) {
                System.out.println(mensaje2);
                return false;
            } else {
                System.out.println("Responda Y/N (SI o NO)");
            }
        }
    }

    /**
     * Maneja el final del turno del jugador actual.
     * <p>
     * Verifica si el turno fue pasado, si la palabra fue cancelada, o si el jugador decidió salir.
     * Si la jugada fue válida, actualiza el tablero y el turno.
     *
     * @param atrilCopia Una copia del atril del jugador antes de la jugada.
     */
    private void manejarFinTurno(Ficha[] atrilCopia) {
        if (turnoPasado) {
            System.out.println("Usted ha pasado su turno!");
            jugadorActual.setFichas(atrilCopia);
            tableroAuxiliar = new Tablero(this.tablero);
            turnoActual = (turnoActual + 1) % jugadores.length;
            return;
        } else if (palabraCancelada) {
            System.out.println("Usted cancelo su palabra, coloque otra!");
            jugadorActual.setFichas(atrilCopia);
            if (primeraJugada) {
                primeraFichaPuesta = false;
            }
            tableroAuxiliar = new Tablero(this.tablero);
            return;
        } else if (salir) {
            System.out.println("Regresando al menu principal...");
            jugadorActual.setFichas(atrilCopia);
            if (primeraJugada) {
                primeraFichaPuesta = false;
            }
            tableroAuxiliar = new Tablero(this.tablero);
            return;
        }

        if (!indiceFichasPuestas.isEmpty()) {
            if (!verificarIndicesValidos(indiceFichasPuestas)) {
                System.out.println("Usted hizo una jugada inválida!");
                jugadorActual.setFichas(atrilCopia);
                tableroAuxiliar = new Tablero(this.tablero);
            } else if (palabraExtractor.verificarPalabrasFormadas(indiceFichasPuestas, tableroAuxiliar, jugadorActual)) {
                turnoActual = (turnoActual + 1) % jugadores.length;
                primeraJugada = false;
                tablero = new Tablero(this.tableroAuxiliar);
            } else {
                System.out.println("La palabra que puso no es válida!");
                jugadorActual.setFichas(atrilCopia);
                if (primeraJugada) {
                    primeraFichaPuesta = false;
                }
                tableroAuxiliar = new Tablero(this.tablero);
            }
        } else {
            System.out.println("No ha ingresado una palabra!");
            jugadorActual.setFichas(atrilCopia);
            tableroAuxiliar = new Tablero(this.tablero);
        }
    }
    /**
     * Verifica si los índices de las fichas colocadas son válidos.
     * <p>
     * El método comprueba si las fichas se colocan en la misma fila o columna.
     *
     * @param indices Lista de índices de las fichas colocadas.
     * @return true si los índices son válidos, false en caso contrario.
     */
    private boolean verificarIndicesValidos(ArrayList<int[]> indices) {
        indices.removeIf(Objects::isNull);
        if (indices.isEmpty()){
            return false;
        }

        int i = indices.getFirst()[0];
        int j = indices.getFirst()[1];

        for(int[] indice : indices) {
            if (indice[0] != i && indice[1] != j) {
                return false;
            }
        }
        return true;
    }

    /**
     * Permite al jugador colocar la primera ficha en el tablero.
     * <p>
     * La primera ficha debe colocarse en el centro del tablero.
     *
     * @param jugador El jugador que coloca la ficha.
     * @return Un array con las coordenadas de la ficha colocada.
     */
    private int[] ponerPrimeraFicha(Jugador jugador){
        int i = tablero.getFilaCentral();
        int j = tablero.getColumnaCentral();

        while(true){
            try {
                int key = controlador.capturarEntrada();

                if (key == 'p' || key == 'P') {
                    System.out.println("¡Has presionado 'P'! Escoge que Ficha poner...");
                    int indice = obtenerEntradaNumerica();
                    if (indice == 9){
                        return null;
                    }
                    if (!jugador.jugarPrimeraFicha(tableroAuxiliar, indice)) {
                        return null;
                    }
                    break;
                }else{
                    System.out.println("Presiona 'P' para poder escoger una ficha");
                }

            } catch (IOException e) {
                System.out.println("Error al capturar entrada");
            }

        }
        return new int[]{i, j};
    }

    /**
     * Permite al jugador colocar una ficha en el tablero.
     * <p>
     * El método captura la entrada del usuario para determinar la posición y la ficha a colocar.
     *
     * @param jugador El jugador que coloca la ficha.
     * @return Un array con las coordenadas de la ficha colocada.
     */
    private int[] ponerFicha(Jugador jugador){
        int i = tablero.getFilaCentral();
        int j = tablero.getColumnaCentral();

        while(true){
            try {
                int key = controlador.capturarEntrada();

                if (key == 'p' || key == 'P') {
                    System.out.println("¡Has presionado 'P'! Escoge que Ficha poner...");
                    int indice = obtenerEntradaNumerica();
                    tableroAuxiliar.desresaltarPosicion(i, j);

                    if (indice == 9){
                        return null;
                    }

                    if (!jugador.jugarFichas(tableroAuxiliar, i , j, indice)) {
                        return null;
                    }
                    break;

                } else if (key != 'w' && key != 'W' && key != 'S' && key != 's' && key != 'a' && key != 'A' && key != 'd' && key != 'D') {
                    System.out.println("Presione alguna de estas teclas para moverse W A S D, presione P para escoger que ficha poner");
                    System.out.println("Para cancelar presione P y luego 9");
                }

                // Mueve el jugador y actualiza la posición
                int[] nuevaPosicion = controlador.moverJugador(i, j, key);
                i = nuevaPosicion[0];
                j = nuevaPosicion[1];

                tableroAuxiliar.mostrarTablero();
                mostrarFichasEIndices(jugador);

            } catch (IOException e) {
                System.out.println("Error al capturar entrada");
            }

        }
        return new int[]{i, j};
    }

    /**
     * Solicita una entrada numérica al usuario.
     *
     * @return El número ingresado por el usuario.
     */
    public int obtenerEntradaNumerica() {
        int opcion = -1;
        boolean entradaValida = false;

        System.out.println("Ingresa el indice de la ficha que quieres jugar (0 a 6):");
        System.out.println("Ingresa 9 para cancelar");
        while (!entradaValida) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion >= 0 && opcion <= 6) {
                    entradaValida = true;
                } else if (opcion == 9) {
                    return opcion;
                }else {
                    System.out.println("Entrada invalida! Ingresa un indice valido (0 a 6):");
                }
            } else {
                System.out.println("Entrada invalida! Ingresa un numero entero:");
                scanner.next();
            }
        }
        return opcion;
    }

    /**
     * Muestra las fichas del jugador actual y sus índices.
     *
     * @param jugador El jugador cuyas fichas se muestran.
     */
    private void mostrarFichasEIndices(Jugador jugador){
        jugador.mostrarFichas();
        int contadorIndices = 0;
        StringBuilder indices = new StringBuilder();
        for (Ficha _ : jugador.getFichas()) {
            indices.append("[").append(contadorIndices).append(" ]");
            contadorIndices++;
        }
        System.out.println(indices);
    }

    /**
     * Rellena las fichas de todos los jugadores desde la bolsa de fichas.
     *
     * @param jugadores Array de jugadores.
     * @param bolsaFichas La bolsa de fichas de donde se sacan las fichas.
     */
    private void rellenarFichasJugadores(Jugador[] jugadores, BolsaFichas bolsaFichas){
        for(Jugador jugador : jugadores){
            jugador.rellenarFichas(bolsaFichas);
        }
    }

    /**
     * Verifica si la partida ha terminado.
     * <p>
     * La partida termina si el jugador actual no tiene más fichas y la bolsa de fichas está vacía.
     *
     * @param jugadorActual El jugador actual.
     * @param bolsaFichas La bolsa de fichas.
     * @return true si la partida continúa, false si ha terminado.
     */
    private boolean partidaTerminada(Jugador jugadorActual, BolsaFichas bolsaFichas) {
        if (jugadorActual.fichasIsEmpty() && bolsaFichas.getListaFichas().isEmpty()){
            partidaTerminada = true;
            return false;
        }else{
            return true;
        }
    }
    /**
     * Muestra el resultado final del juego, indicando el jugador ganador.
     * <p>
     * Recorre la lista de jugadores para encontrar el que tiene la mayor puntuación y lo declara como ganador.
     */
    private void mostrarResultadoFinal() {
        System.out.println("\nLa partida ha terminado y el ganador es:");
        int mayor = 0;
        Jugador ganador = null;
        for (Jugador jugador : jugadores) {
            if(jugador.getScore() > mayor){
                mayor = jugador.getScore();
                ganador = jugador;
            }
        }
        if (ganador != null) {
            System.out.println(ganador.getAlias());
            System.out.println("Con " + ganador.getScore() + " puntos !!!");
            System.out.println("Y con " + ganador.getCantidadPalabrasColocadas() + " Palabras colocadas!!!");
        }
    }

    /**
     * Verifica si la partida ha terminado.
     *
     * @return true si la partida ha terminado, false en caso contrario.
     */
    public boolean isPartidaTerminada() {

        return partidaTerminada;
    }

    /**
     * Genera una clave única para los jugadores, basada en sus alias ordenados alfabéticamente.
     *
     * @return una cadena con los alias de los jugadores separados por guiones bajos.
     */
    public String getClaveJugadores() {
        return Arrays.stream(jugadores).map(Jugador::getAlias).sorted().collect(Collectors.joining("_"));
    }

    /**
     * Actualiza los datos del usuario en el archivo JSON.
     * <p>
     * Carga los datos de los usuarios, encuentra el usuario correspondiente y actualiza sus atributos.
     *
     * @param jugadorActualizado El jugador con los datos actualizados.
     */
    public void actualizarDatosUsuario(Jugador jugadorActualizado) {
        // Cargar usuarios desde el archivo JSON
        List<UsuarioData> usuarios = JsonUtil.cargarUsuariosData();

        // Encontrar y actualizar los atributos de usuario correspondientes
        for (int i = 0; i < usuarios.size(); i++) {
            UsuarioData usuario = usuarios.get(i);
            if (usuario.getAlias().equals(jugadorActualizado.getAlias())) {
                // Actualizar solo los atributos de usuario
                usuario.setCorreoElectronico(jugadorActualizado.getCorreoElectronico());
                usuario.setScoreTotal(jugadorActualizado.getScoreTotal());
                usuario.setHorasJugadas(jugadorActualizado.getHorasJugadas());
                usuario.setMinutosJugados(jugadorActualizado.getMinutosJugados());
                usuario.setSegundosJugados(jugadorActualizado.getSegundosJugados());
                usuario.setCantidadDePalabras(jugadorActualizado.getCantidadDePalabras());
                break;
            }
        }

        // Guardar la lista actualizada de vuelta en el archivo JSON
        JsonUtil.guardarUsuarios(usuarios);
    }

    /**
     * Actualiza los totales de puntos y palabras colocadas para un jugador.
     * <p>
     * Suma los puntos y las palabras colocadas en la partida actual a los totales del jugador.
     *
     * @param jugador El jugador cuyos totales se actualizan.
     */
    public void actualizarTotalesJugador(Jugador jugador) {
        // Sumar puntos de la partida actual al puntaje total
        jugador.setScoreTotal(jugador.getScoreTotal() + jugador.getScoreInGame());

        // Sumar cantidad de palabras colocadas en la partida actual al total
        jugador.setCantidadDePalabras(jugador.getCantidadDePalabras() + jugador.getCantidadPalabrasColocadas());
    }

    /**
     * Actualiza el tiempo jugado en la partida actual para un jugador.
     * <p>
     * Convierte la duración proporcionada en horas, minutos y segundos, y actualiza los atributos correspondientes del jugador.
     *
     * @param jugador  El jugador cuyo tiempo se actualiza.
     * @param tiempoAcumulado La duración del tiempo jugado en la partida actual.
     */
    public void actualizarTiempoInGame(Jugador jugador, long tiempoAcumulado) {
        long horas = tiempoAcumulado / 3600;
        long minutos = (tiempoAcumulado % 3600) / 60;
        long segundos = tiempoAcumulado % 60;

        jugador.setHorasJugadasInGame((int) horas);
        jugador.setMinutosJugadosInGame((int) minutos);
        jugador.setSegundosJugadosInGame((int) segundos);

        jugador.setTiempoJugado(jugador.getHorasJugadasInGame() + "h " + jugador.getMinutosJugadosInGame() + "m " + jugador.getSegundosJugadosInGame() + "s");
    }


    /**
     * Actualiza el tiempo total jugado por un jugador.
     * <p>
     * Suma el tiempo jugado en la partida actual al tiempo total del jugador, ajustando horas y minutos si es necesario.
     *
     * @param jugador El jugador cuyo tiempo total se actualiza.
     */
    public void actualizarTiempoTotal(Jugador jugador) {
        int totalHoras = jugador.getHorasJugadas() + jugador.getHorasJugadasInGame();
        int totalMinutos = jugador.getMinutosJugados() + jugador.getMinutosJugadosInGame();
        int totalSegundos = jugador.getSegundosJugados() + jugador.getSegundosJugadosInGame();

        if (totalSegundos >= 60) {
            totalMinutos += totalSegundos / 60;
            totalSegundos = totalSegundos % 60;
        }

        if (totalMinutos >= 60) {
            totalHoras += totalMinutos / 60;
            totalMinutos = totalMinutos % 60;
        }

        jugador.setHorasJugadas(totalHoras);
        jugador.setMinutosJugados(totalMinutos);
        jugador.setSegundosJugados(totalSegundos);
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    /**
     * Calcula el tiempo transcurrido desde el último inicio y lo agrega al tiempo acumulado.
     * <p>
     * Este método se utiliza para actualizar el tiempo jugado acumulado basado en el tiempo
     * transcurrido desde la última actualización.
     */
    public void actualizarTiempoJugado() {
        if (tiempoInicio > 0) {
            long tiempoActual = Instant.now().getEpochSecond();
            long duracionTemporal = tiempoActual - tiempoInicio;
            tiempoAcumulado += duracionTemporal;
            tiempoInicio = Instant.now().getEpochSecond();
        }
    }


}

