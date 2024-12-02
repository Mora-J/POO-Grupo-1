import com.google.gson.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La clase {@code LectorDeArchivo} proporciona métodos para leer datos de usuarios
 * desde un archivo JSON.
 */
public class LectorDeArchivo {
    private static final String ruta = "C:\\Users\\LAB_AUDIOV\\Downloads\\PROYECTO Juego Scrabble\\";
    private static final String archivoPartidasPendientes = ruta + "partidas_pendientes.json";
    private static final String archivoPartidasTerminadas = ruta + "partidas_terminadas.json";
    private static final Gson gson = new Gson();

    /**
     * Lee los datos de los usuarios desde un archivo JSON especificado y los agrega
     * a la lista de usuarios proporcionada.
     *
     * @param usuarios la lista de usuarios donde se agregarán los datos leídos.
     * @param nombreArchivo el nombre del archivo JSON desde el cual se leerán los datos.
     */
    public void leerDatos(ListaUsuarios usuarios, String nombreArchivo) {
        JSONParser lectorDeDatos = new JSONParser();

        try (FileReader fileReader = new FileReader(nombreArchivo)) {

            JSONArray arregloJSON = (JSONArray) lectorDeDatos.parse(fileReader);

            for (Object object : arregloJSON) {
                JSONObject auxUsuario = (JSONObject) object; // Se le hace down casting para que se vuelva un objeto JSON

                String alias = (String) auxUsuario.get("alias"); // Se obtienen los datos del JSON
                String correo = (String) auxUsuario.get("correo electronico");
                int puntosTotales = Integer.parseInt(auxUsuario.get("score total").toString());
                int horasJugadas = Integer.parseInt(auxUsuario.get("horasJugadas").toString());
                int minutosJugados = Integer.parseInt(auxUsuario.get("minutosJugados").toString());
                int segundosJugados = Integer.parseInt(auxUsuario.get("segundosJugados").toString());
                int cantidadDePalabras = Integer.parseInt(auxUsuario.get("cantidadDePalabras").toString());
                Usuario usuario = new Usuario(alias, correo, puntosTotales, horasJugadas, minutosJugados, segundosJugados, cantidadDePalabras);
                // Se consolida el usuario
                usuarios.agregarUsuario(usuario);
            }

        } catch (IOException | ParseException e) {
            System.out.println("Archivo no encontrado!"); // En caso de que aun no se haya agregado ningun jugador o haya un error con el archivo
        }
    }

    /**
     * Carga las partidas pendientes desde el archivo JSON.
     * <p>
     * Este método llama a {@link #cargarPartidasDesdeArchivo(String)} para cargar las partidas pendientes desde el archivo JSON.
     *
     * @return Una lista de objetos JsonObject que representan las partidas pendientes.
     */
    public static List<JsonObject> cargarPartidasPendientes() {
        return cargarPartidasDesdeArchivo(archivoPartidasPendientes);
    }

    /**
     * Carga las partidas terminadas desde el archivo JSON.
     * <p>
     * Este método llama a {@link #cargarPartidasDesdeArchivo(String)} para cargar las partidas terminadas desde el archivo JSON.
     *
     * @return Una lista de objetos JsonObject que representan las partidas terminadas.
     */
    public static List<JsonObject> cargarPartidasTerminadas() {
        return cargarPartidasDesdeArchivo(archivoPartidasTerminadas);
    }

    /**
     * Carga las partidas desde el archivo JSON especificado.
     * <p>
     * Este método lee el contenido del archivo JSON, parsea los datos en objetos JsonObject y los almacena en una lista.
     * Si ocurre un error durante la lectura del archivo, se imprime un mensaje de error.
     *
     * @param archivo La ruta del archivo JSON desde el cual se cargarán las partidas.
     * @return Una lista de objetos JsonObject que representan las partidas.
     */
    private static List<JsonObject> cargarPartidasDesdeArchivo(String archivo) {
        List<JsonObject> partidas = new ArrayList<>();
        try (FileReader reader = new FileReader(archivo)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                partidas.add(entry.getValue().getAsJsonObject());
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error, revise si la RUTA donde estan las partidas TERMINADAS es la correcta");
            System.out.println("SI ESTE ERROR APARECE ES PROBABLE QUE NO HAY PARTIDAS TERMINADAS O PENDIENTES");
        }
        return partidas;
    }

    /**
     * Imprime todas las partidas en una tabla.
     * <p>
     * Este método carga las partidas pendientes y terminadas desde los archivos JSON y las imprime en una tabla.
     * La tabla contiene los jugadores, sus puntajes, la cantidad de palabras colocadas, el tiempo jugado y el estado de la partida.
     */
    public static void imprimirPartidasEnTabla() {
        List<JsonObject> partidasPendientes = cargarPartidasPendientes();
        List<JsonObject> partidasTerminadas = cargarPartidasTerminadas();

        System.out.println("\n+-----------------+------------+-------------------+--------------+-----------+");
        System.out.printf("| %-15s | %-10s | %-17s | %-12s | %-9s |\n", "Jugador", "Puntaje", "Cantidad de Palabras", "Tiempo Jugado", "Estado");
        System.out.println("+-----------------+------------+-------------------+--------------+-----------+");

        for (JsonObject partida : partidasPendientes) {
            imprimirPartidaEnFila(partida, "En curso");
        }

        for (JsonObject partida : partidasTerminadas) {
            imprimirPartidaEnFila(partida, "Terminada");
        }

        System.out.println("+-----------------+------------+-------------------+--------------+-----------+");
    }

    /**
     * Imprime los detalles de una partida en una fila de la tabla.
     * <p>
     * Este método extrae la información de cada jugador en la partida JSON y la imprime en una fila de la tabla.
     * La información incluye el alias del jugador, el puntaje, la cantidad de palabras colocadas, el tiempo jugado y el estado de la partida.
     *
     * @param partida El objeto JSON que representa la partida.
     * @param estado El estado de la partida, que puede ser "En curso" o "Terminada".
     */
    private static void imprimirPartidaEnFila(JsonObject partida, String estado) {
        JsonArray jugadoresArray = partida.getAsJsonArray("jugadores");

        for (int i = 0; i < jugadoresArray.size(); i++) {
            JsonObject jugadorObj = jugadoresArray.get(i).getAsJsonObject();
            String jugador = jugadorObj.get("alias").getAsString();
            int puntaje = jugadorObj.get("scoreInGame").getAsInt();
            int cantidadPalabras = jugadorObj.get("cantidadPalabrasColocadas").getAsInt();
            String tiempoJugado = jugadorObj.get("tiempoJugado").getAsString();

            System.out.printf("| %-15s | %-10d | %-17d | %-12s | %-9s |\n",
                    jugador, puntaje, cantidadPalabras, tiempoJugado, estado);
        }
    }


}

