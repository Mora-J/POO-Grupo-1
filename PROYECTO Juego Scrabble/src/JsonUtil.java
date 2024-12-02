import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Clase JsonUtil.
 * Utilizada para guardar y cargar partidas pendientes y terminadas de un juego en formato JSON.
 */
public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String archivoPartidasPendientes = "partidas_pendientes.json";
    private static final String archivoPartidasTerminadas = "partidas_terminadas.json";
    // Aquí debe de poner la ruta donde se guardan los usuarios
    private static final String ruta = "C:\\Users\\LAB_AUDIOV\\Downloads\\RegistroUsuario\\";

    private static final String archivoUsuarios = ruta + "usuarios.json";

    /**
     * Guarda una partida pendiente.
     * Agrega la partida a la colección de partidas pendientes y la guarda en un archivo JSON.
     * @param juego La instancia del juego a guardar.
     */
    public static void guardarPartidaPendiente(Juego juego) {
        Map<String, Juego> partidasPendientes = cargarTodasLasPartidasPendientes();
        String claveJugadores = juego.getClaveJugadores();
        partidasPendientes.put(claveJugadores, juego);
        guardarPartidasPendientes(partidasPendientes);
    }

    /**
     * Guarda una partida terminada.
     * Agrega la partida a la colección de partidas terminadas y la guarda en un archivo JSON.
     * @param juego La instancia del juego a guardar.
     */
    public static void guardarPartidaTerminada(Juego juego) {
        Map<String, Juego> partidasTerminadas = cargarTodasLasPartidasTerminadas();
        String claveJugadores = juego.getClaveJugadores();
        partidasTerminadas.put(claveJugadores, juego);
        guardarPartidasTerminadas(partidasTerminadas);
    }

    /**
     * Carga una partida pendiente.
     * @param claveJugadores La clave única de los jugadores que identifica la partida.
     * @return La instancia del juego cargado, o null si no se encuentra.
     */
    public static Juego cargarPartidaPendiente(String claveJugadores) {
        Map<String, Juego> partidasPendientes = cargarTodasLasPartidasPendientes();
        return partidasPendientes.getOrDefault(claveJugadores, null);
    }

    /**
     * Carga todas las partidas pendientes desde el archivo JSON.
     * @return Un mapa con todas las partidas pendientes.
     */
    public static Map<String, Juego> cargarTodasLasPartidasPendientes() {
        try (FileReader reader = new FileReader(archivoPartidasPendientes)) {
            Type mapType = new TypeToken<HashMap<String, Juego>>() {}.getType();
            return gson.fromJson(reader, mapType);
        } catch (IOException e) {
            return new HashMap<>(); // Retorna un mapa vacío si el archivo no existe
        }
    }

    /**
     * Carga todas las partidas terminadas desde el archivo JSON.
     * @return Un mapa con todas las partidas terminadas.
     */
    public static Map<String, Juego> cargarTodasLasPartidasTerminadas() {
        try (FileReader reader = new FileReader(archivoPartidasTerminadas)) {
            Type mapType = new TypeToken<HashMap<String, Juego>>() {}.getType();
            return gson.fromJson(reader, mapType);
        } catch (IOException e) {
            return new HashMap<>(); // Retorna un mapa vacío si el archivo no existe
        }
    }

    /**
     * Guarda todas las partidas pendientes en el archivo JSON.
     * @param partidasPendientes Un mapa con todas las partidas pendientes.
     */
    private static void guardarPartidasPendientes(Map<String, Juego> partidasPendientes) {
        try (FileWriter writer = new FileWriter(archivoPartidasPendientes)) {
            gson.toJson(partidasPendientes, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo " + archivoPartidasPendientes);
        }
    }

    /**
     * Guarda todas las partidas terminadas en el archivo JSON.
     * @param partidasTerminadas Un mapa con todas las partidas terminadas.
     */
    private static void guardarPartidasTerminadas(Map<String, Juego> partidasTerminadas) {
        try (FileWriter writer = new FileWriter(archivoPartidasTerminadas)) {
            gson.toJson(partidasTerminadas, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo " + archivoPartidasTerminadas);
        }
    }

    /**
     * Carga una lista de objetos Jugador desde el archivo de usuarios.
     * <p>
     * Este método lee el archivo JSON que contiene la información de los jugadores,
     * crea una lista de objetos Jugador y completa los atributos faltantes con valores predeterminados.
     *
     * @return una lista de objetos Jugador. Si ocurre un error al leer el archivo, se retorna una lista vacía.
     */
    public static List<Jugador> cargarUsuarios() {
        try (FileReader reader = new FileReader(archivoUsuarios)) {
            Type listType = new TypeToken<ArrayList<Jugador>>() {}.getType();
            List<Jugador> jugadores = gson.fromJson(reader, listType);

            // Completar los atributos faltantes en cada jugador
            for (Jugador jugador : jugadores) {
                jugador.setHorasJugadasInGame(0);
                jugador.setMinutosJugadosInGame(0);
                jugador.setSegundosJugadosInGame(0);
                jugador.setScoreInGame(0);
                jugador.setTiempoJugado("0h 0m 0s");
                jugador.setCantidadPalabrasColocadas(0);
                jugador.setFichas(new Ficha[7]);
            }

            return jugadores;
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
        }
    }

    /**
     * Carga una lista de objetos UsuarioData desde el archivo de usuarios.
     * <p>
     * Este método lee el archivo JSON que contiene la información de los usuarios y
     * crea una lista de objetos UsuarioData.
     *
     * @return una lista de objetos UsuarioData. Si ocurre un error al leer el archivo, se retorna una lista vacía.
     */
    public static List<UsuarioData> cargarUsuariosData() {
        try (FileReader reader = new FileReader(archivoUsuarios)) {
            Type listType = new TypeToken<ArrayList<UsuarioData>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
        }
    }

    /**
     * Guarda una lista de objetos UsuarioData en el archivo de usuarios.
     * <p>
     * Este método convierte la lista de objetos UsuarioData en formato JSON y lo guarda en el archivo especificado.
     *
     * @param usuarios la lista de objetos UsuarioData a guardar.
     */
    public static void guardarUsuarios(List<UsuarioData> usuarios) {
        try (FileWriter writer = new FileWriter(archivoUsuarios)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}
