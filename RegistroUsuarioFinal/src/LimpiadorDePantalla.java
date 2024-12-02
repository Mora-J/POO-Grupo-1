/**
 * La clase {@code LimpiadorDePantalla} proporciona un método para limpiar la consola.
 */
public class LimpiadorDePantalla {

    /**
     * Limpia la consola usando secuencias de escape ANSI.
     */
    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
