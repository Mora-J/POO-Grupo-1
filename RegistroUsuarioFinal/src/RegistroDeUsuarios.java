import java.util.Scanner;

/**
 * La clase RegistroDeUsuarios se encarga de gestionar un menú interactivo para la creación,
 * modificación, visualización y eliminación de usuarios. Utiliza múltiples clases auxiliares
 * como ListaUsuarios, Menu, LectorDeArchivo, EscritorDeArchivo y LimpiadorDePantalla para
 * proporcionar estas funcionalidades.
 */
public class RegistroDeUsuarios {

    public static void main(String[] args) {
        // El primero establece el color del texto a morado y el segundo lo restablece al color original de la consola
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RESET = "\u001B[0m";

        ListaUsuarios listaUsuarios = new ListaUsuarios();
        Menu menu = new Menu();
        LectorDeArchivo lectorArchivo = new LectorDeArchivo();
        EscritorDeArchivo escritorArchivo = new EscritorDeArchivo();
        LimpiadorDePantalla limpiadorPantalla = new LimpiadorDePantalla();
        Scanner input = new Scanner(System.in);
        Scanner entrada = new Scanner(System.in);
        String auxInput = "";
        int opcionMenu = 0;

        System.out.println(ANSI_PURPLE + "+++----------" + ANSI_RESET +"¡Bienvenido al de Registro de Usuario!" + ANSI_PURPLE + "----------+++" + ANSI_RESET);
        lectorArchivo.leerDatos(listaUsuarios, "usuarios.json"); // Busca el archivo, si ya existe lee sus datos
        System.out.println("Presione ENTER para continuar...");
        input.nextLine();
        boolean salir = true;

        while (salir) {

            limpiadorPantalla.limpiarPantalla();
            menu.mostrarMenu();
            auxInput = entrada.next();
            opcionMenu = menu.obtenerOpcion(auxInput);

            switch (opcionMenu) {
                case 1:
                    limpiadorPantalla.limpiarPantalla();
                    System.out.println(ANSI_PURPLE + "+++-----------------------" + ANSI_RESET + "Creacion de Usuario" + ANSI_PURPLE + "-----------------------+++" + ANSI_RESET);
                    listaUsuarios.agregarUsuario(listaUsuarios.crearUsuario());
                    System.out.println(ANSI_PURPLE + "Usuario creado exitosamente!" + ANSI_RESET);
                    System.out.println("Presione ENTER para continuar...");
                    input.nextLine();
                    break;

                case 2:
                    limpiadorPantalla.limpiarPantalla();
                    System.out.println(ANSI_PURPLE + "+++-----------------------" + ANSI_RESET + "Modificacion de Usuario" + ANSI_PURPLE + "-----------------------+++" + ANSI_RESET);
                    listaUsuarios.modificarUsuario();
                    System.out.println("Presione ENTER para continuar...");
                    input.nextLine();
                    break;

                case 3:
                    limpiadorPantalla.limpiarPantalla();
                    System.out.println(ANSI_PURPLE + "+++-----------------------------" + ANSI_RESET + "Usuarios Agregados" + ANSI_PURPLE + "----------------------------+++" + ANSI_RESET);
                    listaUsuarios.mostrarDatos();
                    System.out.println("Presione ENTER para continuar...");
                    input.nextLine();
                    break;

                case 4:
                    limpiadorPantalla.limpiarPantalla();
                    System.out.println(ANSI_PURPLE + "+++-----------------------" + ANSI_RESET + "Eliminacion de Usuario" + ANSI_PURPLE + "-----------------------+++" + ANSI_RESET);
                    listaUsuarios.eliminarUsuario();
                    System.out.println("Presione ENTER para continuar...");
                    input.nextLine();
                    break;
                case 5:
                    LectorDeArchivo.imprimirPartidasEnTabla();
                case 6:
                    salir = false;
                    break;
            }
        }

        // Al terminar el proceso guarda los datos de los usuarios en el JSON
        escritorArchivo.guardarDatos(listaUsuarios, "usuarios.json");
        System.out.println("Hasta la proxima...!");
    }
}
