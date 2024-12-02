import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * La clase RaeVerificador se encarga de verificar palabras en el diccionario de la Real Academia Española (RAE).
 */
public class RaeVerificador {

    /**
     * URL base del diccionario de la RAE.
     */
    private static final String RAE_URL = "https://dle.rae.es/";

    /**
     * Verifica si una palabra es válida según el diccionario de la RAE.
     *
     * @param palabra la palabra a verificar.
     * @return {@code true} si la palabra es válida, {@code false} en caso contrario.
     */
    public static boolean verificarPalabra(String palabra) {
        if (palabra.contains("#")) {
            return false;
        }

        ArrayList<String> variantes = VariantesPalabra.generarVariantes(palabra);
        for (String variante : variantes) {
            if (verificarVariante(variante)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica una variante de una palabra en el diccionario de la RAE.
     *
     * @param variante la variante de la palabra a verificar.
     * @return {@code true} si la variante es válida, {@code false} en caso contrario.
     */
    private static boolean verificarVariante(String variante) {
        String url = RAE_URL + variante;
        int maxRetries = 3;
        int retryCount = 0;
        long timeout = 5000; // 5 segundos

        while (retryCount < maxRetries) {
            try {
                Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout((int) timeout).get();
                Elements elementos = doc.select("#resultados");
                return !elementos.text().startsWith("Aviso");
            } catch (Exception e) {
                retryCount++;
                if (retryCount < maxRetries) {
                    System.out.println("Error al conectar: " + e.getMessage() + ". Reintentando (" + retryCount + "/" + maxRetries + ")");
                }else{
                    System.out.println("Error al conectarse a internet, porfavor verifique su conexion, firewall o proxy de su red ");
                    System.exit(130);
                }
            }
        }
        return false;
    }

}