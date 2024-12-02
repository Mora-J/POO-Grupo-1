/**
 * Esta clase representa una excepción personalizada que se lanza
 * cuando el alias del usuario está vacío.
 *
 * <p>Hereda de {@code RuntimeException} y proporciona un metódo
 * para devolver un mensaje de error específico.</p>
 */
public class AliasVacioException extends RuntimeException {

    /**
     * Devuelve un mensaje indicando que el alias del usuario no puede ser vacío.
     *
     * @return un mensaje de error indicando que el alias del usuario no puede ser vacío.
     */
    public String excAliasVacio() {
        return "El alias del usuario no puede ser vacio";
    }
}
