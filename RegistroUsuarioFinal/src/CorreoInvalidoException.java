/**
 * Esta clase representa una excepción personalizada que se lanza
 * cuando el correo electrónico del usuario no tiene un formato válido.
 *
 * <p>Hereda de {@code RuntimeException} y proporciona métodos para
 * manejar el error de formato de correo electrónico.</p>
 */
public class CorreoInvalidoException extends RuntimeException {

  /**
   * Constructor por defecto para la excepción {@code CorreoInvalidoException}.
   */
  public CorreoInvalidoException() {
  }

  /**
   * Devuelve un mensaje indicando que el formato del correo electrónico
   * no es válido y solicita un nuevo intento.
   *
   * @return un mensaje de error indicando que el formato del correo electrónico no es válido.
   */
  public String excFormatoInvalida() {
    return "El correo no es valido, intentelo de nuevo";
  }
}
