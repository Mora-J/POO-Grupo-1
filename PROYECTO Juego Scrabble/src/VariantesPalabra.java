import java.util.ArrayList;

/**
 * La clase VariantesPalabra se encarga de generar variantes de una palabra
 * considerando las versiones con acentos en las vocales.
 */
public class VariantesPalabra {

    /**
     * Matriz de caracteres que representan las vocales y sus versiones acentuadas.
     */
    private static final char[][] acentos = new char[][]{{'a', 'á'}, {'e', 'é'}, {'i', 'í'}, {'o', 'ó'}, {'u', 'ú'}};

    /**
     * Genera variantes de una palabra reemplazando las vocales por sus versiones acentuadas.
     *
     * @param palabra la palabra original de la cual generar variantes.
     * @return una lista de variantes de la palabra, incluyendo la palabra original y sus versiones acentuadas.
     */
    public static ArrayList<String> generarVariantes(String palabra) {
        palabra = palabra.toLowerCase();
        ArrayList<String> variantes = new ArrayList<>();
        variantes.add(palabra);
        char[] palabraArray = palabra.toCharArray();

        for (int i = 0; i < palabraArray.length; i++) {
            for (char[] vocalAcento : acentos) {
                if (palabraArray[i] == vocalAcento[0]) {
                    char[] palabraAcento = palabraArray.clone();
                    palabraAcento[i] = vocalAcento[1];
                    variantes.add(new String(palabraAcento));
                }
            }
        }
        return variantes;
    }
}
