import java.util.Scanner;

/**
 * La clase {@code Menu} proporciona un menú interactivo para la gestión de usuarios.
 */
public class Menu {

    /**
     * Constructor por defecto para la clase {@code Menu}.
     */
    public Menu() {
    }

    /**
     * Muestra el menú de opciones para la gestión de usuarios.
     */
    public void mostrarMenu() {
        System.out.println("+++----------------------------------------------------------------+++");
        System.out.println("                  Registro de Usuarios");
        System.out.println("             1. Crear un nuevo usuario");
        System.out.println("             2. Modificar datos del usuario");
        System.out.println("             3. Mostrar usuarios");
        System.out.println("             4. Eliminar usuario");
        System.out.println("             5. Mostrar Estadisticas (Partidas)");
        System.out.println("             6. Salir de la aplicacion");
        System.out.println("+++----------------------------------------------------------------+++");
        System.out.print("Ingrese una opcion:");
    }

    /**
     * Obtiene y valida la opción seleccionada por el usuario.
     *
     * @param opcion la opción seleccionada por el usuario en formato de cadena.
     * @return la opción válida seleccionada por el usuario en formato de entero.
     */
    public int obtenerOpcion(String opcion) {
        int opcionValida;
        Scanner entrada = new Scanner(System.in);
        String auxopcion = opcion;
        Validador validar = new Validador();

        while (!validar.validarOpcionMenuPrincipal(auxopcion)) { // Se le pregunta al usuario hasta que la opción sea válida
            System.out.print("Intentelo de nuevo: ");
            auxopcion = entrada.nextLine();
        }
        opcionValida = Integer.parseInt(auxopcion);

        return opcionValida;
    }
}


