/**
 * Esta clase representa una excepción personalizada que se lanza
 * cuando la opción del menú ingresada es inválida.
 *
 * <p>Hereda de {@code Throwable} y proporciona un método para devolver un mensaje de error específico.</p>
 */
public class OpcionMenuInvalidaException extends Throwable {

    /**
     * Constructor por defecto para la excepción {@code OpcionMenuInvalidaException}.
     */
    public OpcionMenuInvalidaException() {

    }

    /**
     * Devuelve un mensaje indicando que la opción ingresada es inválida y solicita una opción válida.
     *
     * @return un mensaje de error indicando que la opción ingresada es inválida.
     */
    public String excOpcionInvalida() {
        return "Error: la opcion ingresada es invalida, eliga una opcion entre 1 y 5";
    }
}
