import java.util.ArrayList;
import java.util.Collections;

/**
 * La clase BolsaFichas crea y almacena todas las fichas del juego.
 */
public class BolsaFichas {
    private final ArrayList<Ficha> listaFichas;

    /**
     * Crea una nueva instancia de BolsaFichas.
     * <p>
     * Inicializa todas las letras del abecedario, la cantidad de fichas para cada letra
     * y su valor correspondiente. Luego, asigna las fichas a la lista y las mezcla.
     */
    public BolsaFichas() {

        String[] letras = new String[]{"A", "B", "C", "CH", "D", "E", "F", "G", "H", "I",
                "J", "L", "LL", "M", "N", "Ñ", "O", "P", "Q", "R", "RR", "S", "T", "U", "V", "X", "Y", "Z", "#"};

        int[] cantidadFichas = new int[]{12, 2, 4, 1, 5, 12, 1, 2, 2, 6, 1, 4, 1, 2, 5, 1,
                9, 2, 1, 5, 1, 6, 4, 5, 1, 1, 1, 1, 2};

        int[] valorFichas = new int[]{1, 4, 3, 8, 3, 1, 5, 3, 5, 1, 10, 2, 8, 3, 2, 10, 1,
                4, 8, 2, 8, 1, 2, 1, 4, 10, 5, 10, 0};

        listaFichas = new ArrayList<>();
        for (int i = 0; i < letras.length; i++) {
            for (int j = 0; j < cantidadFichas[i]; j++) {
                this.listaFichas.add(new Ficha(valorFichas[i], letras[i]));
            }
        }

        // Mezcla las fichas.
        Collections.shuffle(this.listaFichas);
    }

    /**
     * Devuelve la lista de fichas disponibles.
     *
     * @return lista de fichas.
     */
    public ArrayList<Ficha> getListaFichas() {
        return listaFichas;
    }
}
