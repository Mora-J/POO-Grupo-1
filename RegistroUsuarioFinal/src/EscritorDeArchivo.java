import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La clase {@code EscritorDeArchivo} proporciona métodos para guardar datos de usuarios
 * en un archivo JSON.
 */
public class EscritorDeArchivo {

    /**
     * Guarda los datos de los usuarios en un archivo JSON especificado.
     *
     * @param usuarios la lista de usuarios a guardar.
     * @param nombreArchivo el nombre del archivo JSON donde se guardarán los datos.
     */
    public void guardarDatos(ListaUsuarios usuarios, String nombreArchivo) {
        JSONArray arregloJSON = new JSONArray();
        for (Usuario usuario : usuarios.getListaUsuarios()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("alias", usuario.getAlias().toLowerCase()); // Primero crea un Objeto JSON, donde se van asignando los datos
            jsonObject.put("correo electronico", usuario.getCorreoElectronico().toLowerCase());
            jsonObject.put("score total", usuario.getScoreTotal());
            jsonObject.put("minutosJugados", usuario.getMinutosJugados());
            jsonObject.put("segundosJugados", usuario.getSegundosJugados());
            jsonObject.put("horasJugadas", usuario.getHorasJugadas());
            jsonObject.put("cantidadDePalabras", usuario.getCantidadDePalabras());
            arregloJSON.add(jsonObject); // Los datos obtenidos anteriormente se agregan a un JSONarray
        }

        try (FileWriter archivo = new FileWriter(nombreArchivo)) {
            archivo.write(arregloJSON.toJSONString()); // Teniendo el JSONarray finalmente se colocan en el archivo JSON
            System.out.println("Datos guardados exitosamente!");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error para guardar los datos en el archivo!");
        }
    }
}
