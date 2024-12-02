import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La clase Validar proporciona métodos para validar diferentes tipos de entrada, como correos electrónicos,
 * números y opciones de menús. También verifica si los alias están vacíos y si los correos electrónicos son únicos.
 */
public class Validador {

    /**
     * Constructor vacío de la clase Validar.
     */
    public Validador() {
    }

    /**
     * Valida si la estructura del correo electrónico es válida según una expresión regular.
     *
     * @param email el correo electrónico a validar
     * @return true si el correo electrónico tiene una estructura válida, false en caso contrario
     */
    private boolean validaEstructuraCorreo(String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Valida si un correo electrónico es válido, lanzando una excepción si no lo es.
     *
     * @param correo el correo electrónico a validar
     * @throws CorreoInvalidoException si el correo electrónico no es válido
     */
    public void validarCorreo(String correo) throws CorreoInvalidoException {
        if (!validaEstructuraCorreo(correo))
            throw new CorreoInvalidoException();
    }

    /**
     * Valida si la opción del menú principal es correcta, lanzando una excepción si no lo es.
     *
     * @param validacion la opción del menú a validar
     * @throws OpcionMenuInvalidaException si la opción no es válida (no está en el rango 1-5)
     */
    public void validarNumeroOpcion(int validacion) throws OpcionMenuInvalidaException {
        if ((validacion <= 0) || (validacion >= 6))
            throw new OpcionMenuInvalidaException();
    }

    /**
     * Verifica si una cadena ingresada es un número entero.
     *
     * @param opcion la cadena a verificar
     * @return true si la cadena es un número entero, false en caso contrario
     */
    public boolean validarIsInt(String opcion) {
        try {
            Integer.parseInt(opcion);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Ingrese uno de los numeros mostrados anteriormente");
            return false;
        }
    }

    public boolean comprobarAliasIguales(String alias, ListaUsuarios usuarios){
        boolean boolValidar = true;
        for (Usuario usuario: usuarios.getListaUsuarios()){
            if (Objects.equals(usuario.getAlias(), alias)){
                boolValidar = false;
                break;
            }
        } return boolValidar;
    }
    /**
     * Verifica si un alias está vacío.
     *
     * @param alias el alias a verificar
     * @return true si el alias está vacío, false en caso contrario
     */
    public boolean validarAliasIsEmpty(String alias) {
        return alias.trim().isEmpty();
    }

    /**
     * Valida si un alias es válido, lanzando una excepción si está vacío.
     *
     * @param alias el alias a validar
     * @throws AliasVacioException si el alias está vacío
     */
    public void validarAliasUsuario(String alias) throws AliasVacioException {
        if (validarAliasIsEmpty(alias)){
            throw new AliasVacioException();
        }
    }

    /**
     * Verifica si una posición es válida dentro de una lista de usuarios.
     *
     * @param posicion la posición a verificar
     * @param usuariosSize el tamaño de la lista de usuarios
     * @return true si la posición es válida, false en caso contrario
     */
    public boolean comprobarPosicion(int posicion, int usuariosSize) {
        if((usuariosSize > posicion - 1) && (posicion > 0)){
            return true;
        } else {
            System.out.println("Opcion no valida. Ingrese un numero valido");
            return false;
        }
    }

    /**
     * Verifica si un correo electrónico ya existe en la lista de usuarios.
     *
     * @param correo el correo electrónico a verificar
     * @param usuarios la lista de usuarios
     * @return true si el correo no existe en la lista, false en caso contrario
     */
    public boolean comprobarCorreosIguales(String correo, ListaUsuarios usuarios) {
        boolean boolValidar = true;
        for (Usuario elemento : usuarios.getListaUsuarios()){
            if (Objects.equals(elemento.getCorreoElectronico(), correo)){
                boolValidar = false;
                break;
            }
        }
        return boolValidar;
    }

    /**
     * Verifica si una opción es válida (1 o 2).
     *
     * @param opcion la opción a verificar
     * @return true si la opción es válida, false en caso contrario
     */
    public boolean validarOpcion1o2(String opcion) {
        if(validarIsInt(opcion)) {
            int opcionInt = Integer.parseInt(opcion);
            if((opcionInt > 0) && (opcionInt < 3)){
                return true;
            } else {
                System.out.println("Ingrese una opcion valida ( 1 o 2 )");
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Verifica si la opción del menú principal es válida, lanzando excepciones si no lo es.
     *
     * @param opcion la opción del menú a verificar
     * @return true si la opción es válida, false en caso contrario
     */
    public boolean validarOpcionMenuPrincipal(String opcion) {
        try {
            int auxOpcion = Integer.parseInt(opcion);
            validarNumeroOpcion(auxOpcion);
            return true;
        } catch (NumberFormatException e){
            System.out.println("ERROR: el dato a ingresar debe ser un numero entero entre 1 y 5");
            return false;
        } catch (OpcionMenuInvalidaException e){
            System.out.println(e.excOpcionInvalida());
            return false;
        }
    }
}
