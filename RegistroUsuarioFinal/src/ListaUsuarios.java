import java.util.LinkedList;
import java.util.Scanner;

/**
 * La clase ListaUsuarios gestiona una lista de usuarios y proporciona métodos para agregar y validar usuarios.
 */
public class ListaUsuarios implements MostrarDatos {
    private LinkedList<Usuario> listaUsuarios;

    /**
     * Constructor que inicializa la lista de usuarios con una lista proporcionada.
     *
     * @param listaUsuarios la lista de usuarios a inicializar
     */
    public ListaUsuarios(LinkedList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * Constructor por defecto que inicializa la lista de usuarios vacía.
     */
    public ListaUsuarios() {
        this.listaUsuarios = new LinkedList<Usuario>();
    }

    /**
     * Obtiene la lista de usuarios.
     *
     * @return la lista de usuarios
     */
    public LinkedList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Establece la lista de usuarios.
     *
     * @param listaUsuarios la lista de usuarios a establecer
     */
    public void setListaUsuarios(LinkedList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * Crea un nuevo usuario solicitando los datos necesarios y validándolos.
     *
     * @return el usuario creado
     */
    public Usuario crearUsuario() {
        Usuario usuario = null;
        Scanner entrada = new Scanner(System.in);
        String alias = "", correo;
        System.out.println("                  Ingrese los datos del usuario");
        Validador validar = new Validador();
        boolean salir = true;


        while (salir == true){
            try{
                System.out.print("Alias: ");
                alias = entrada.nextLine();
                validar.validarAliasUsuario(alias);
                if (!validar.comprobarAliasIguales(alias, this)){
                    System.out.println("Ya existe un usuario con ese alias");
                }
                else {
                    salir = false;

                }
            } catch (AliasVacioException e){
                System.out.println(e.excAliasVacio());
            }

        }

        while (usuario == null) {
            try {
                System.out.print("Correo electronico: ");
                correo = entrada.next();
                validar.validarCorreo(correo);
                if (validar.comprobarCorreosIguales(correo, this)) {
                    usuario = new Usuario(alias, correo);
                } else {
                    System.out.println("Ya existe un usuario con ese correo");
                }
            } catch (CorreoInvalidoException e) {
                System.out.println(e.excFormatoInvalida());
                usuario = null;
            }
        }
        return usuario;
    }


    /**
    * Agrega el usuario creado a la lista de usuarios
    * @param usuario
     */
    //Agrega el usuario creado a la lista de usuarios
    public void agregarUsuario(Usuario usuario){
        this.listaUsuarios.add(usuario);
    }

    public int getTamanoLista(){
        return this.listaUsuarios.size();
    }

    /**
    * Modifica el nombre o correo de un usuario
    */

    public void modificarUsuario(){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";
        Scanner entrada = new Scanner(System.in);
        String opcionInput = "";
        int posicion = 0;
        int opcion = 0;
        int opcionAModificar = 0;
        int contador = 0;
        boolean boolValidar = false;
        Validador validar = new Validador();


        if (getTamanoLista() > 0){


            System.out.println("+++----------------------------Usuarios----------------------------+++");
            mostrarNombres();
            System.out.println("Ingrese el numero del usuario que desea modificar: ");


            while(boolValidar == false){
                opcionInput = entrada.next();
                if(validar.validarIsInt(opcionInput) == true){
                    opcionAModificar = Integer.parseInt(opcionInput);
                    if(validar.comprobarPosicion(opcionAModificar, getTamanoLista()) == true){
                        boolValidar = true;
                    }
                }
            }
            opcionAModificar--;
            boolValidar = false;


            System.out.println("+++----------------------------------------------------------------+++");
            System.out.println("¿Que deseas modificar? \n 1. Alias \n 2. Correo electronico");
            System.out.println("+++----------------------------------------------------------------+++");
            while(boolValidar == false){
                opcionInput = entrada.next();
                if (validar.validarOpcion1o2(opcionInput) == true){
                    opcion = Integer.parseInt(opcionInput);
                    boolValidar=true;
                }
            }

            boolValidar = false;

            if(opcion == 1){
                //Cambia el nombre del usuario seleccionado
                System.out.print("Ingrese su nuevo de alias: ");
                try{
                    opcionInput = entrada.next();
                    validar.validarAliasUsuario(opcionInput);
                    if (!validar.comprobarAliasIguales(opcionInput, this)){
                        System.out.println(ANSI_RED + "Ya existe un usuario con ese alias" + ANSI_RESET);
                    }
                    else {
                        listaUsuarios.get(opcionAModificar).setAlias(opcionInput);
                    }


                } catch (AliasVacioException e ){
                    System.out.println(e.excAliasVacio());

                }

                System.out.println(ANSI_PURPLE + "Alias cambiado exitosamente!" + ANSI_RESET);

            }else if(opcion == 2){

                System.out.print("Ingrese su nuevo correo electronico: ");

                while (boolValidar == false){
                    try{
                        opcionInput = entrada.next();
                        validar.validarCorreo(opcionInput);
                        if (validar.comprobarCorreosIguales(opcionInput, this) == true){
                            boolValidar = true;
                        }else{
                            System.out.println(ANSI_RED + "Ya existe un usuario con ese correo" + ANSI_RESET);
                        }
                    } catch (CorreoInvalidoException e) {
                        System.out.println(e.excFormatoInvalida());
                        boolValidar = false;
                    }
                }
                listaUsuarios.get(posicion).setCorreoElectronico(opcionInput);
                System.out.println(ANSI_PURPLE + "Correo cambiado con exito!" + ANSI_RESET);
            }


        }else {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Aun no se ha registrado ningun usuario...");
            System.out.println("----------------------------------------------------------------");
        }

    }

    /**
     * Metodo encarado de eliminar usuarios.
     * Pide al usuario una opcion y comprueba que sea valida.
     * Elimina el ususario de la lista mediante la posicion que ocupe
     */
    //Metodo encargado de eliminar usuarios
    public void eliminarUsuario(){
        final String ANSI_PURPLE = "\u001B[35m";
        Scanner entrada = new Scanner(System.in);
        String auxOpcion = "";
        int opcionAModificar = 0;
        boolean boolValidar = false;
        Validador validar = new Validador();
        final String ANSI_RESET = "\u001B[0m";


        if (getTamanoLista() > 0){
            mostrarNombres();
            System.out.println("Ingrese el numero del usuario que desea eliminar: ");

            while(boolValidar != true){
                auxOpcion = entrada.next();
                if(validar.validarIsInt(auxOpcion) == true){
                    opcionAModificar = Integer.parseInt(auxOpcion);
                    if( validar.comprobarPosicion(opcionAModificar, getTamanoLista()) == true ){
                        boolValidar = true;
                    }
                }
            }
            opcionAModificar--;



            listaUsuarios.remove(opcionAModificar);
            System.out.println(ANSI_PURPLE + "El ha sido eliminado exitosamente" + ANSI_RESET);

        }else {
            System.out.println("----------------------------------------------------------------");
            System.out.println("No se ha registrado ningun usuario...");
            System.out.println("----------------------------------------------------------------");
        }

    }

    /**
     * Metodo encargado de mostrar los datos de los usuarios
     * Muestra los nombres y pregunta si se desea ver mas datos de algun usuario.
     * Si se desea, muestra los datos del usuario seleccionado
     */

    public void mostrarDatos(){
        Scanner entrada = new Scanner(System.in);
        String auxOpcion = "";
        int opcionARevisar = 0;
        boolean boolValidar = false;
        Validador validar = new Validador();
        int opcion = 0 ;
        int contador = 0;

        if (getTamanoLista() > 0){
            //Muestra los nombres
            mostrarNombres();
            System.out.println("+++----------------------------------------------------------------+++");
            System.out.println("Desea ver mas datos de algun usuario? \n 1. Si \n 2. No");
            System.out.println("+++----------------------------------------------------------------+++");

            while(boolValidar == false){
                auxOpcion = entrada.next();
                if (validar.validarOpcion1o2(auxOpcion) == true){
                    opcion = Integer.parseInt(auxOpcion);
                    boolValidar=true;
                }
            }
            boolValidar = false;
            if (opcion == 1){
                System.out.println("Ingrese el numero de usuario que desea ver: ");
                while(boolValidar != true){
                    auxOpcion = entrada.next();
                    if(validar.validarIsInt(auxOpcion) == true){
                        opcionARevisar = Integer.parseInt(auxOpcion);
                        if( validar.comprobarPosicion(opcionARevisar, getTamanoLista()) == true ){
                            boolValidar = true;
                        }
                    }
                }
                opcionARevisar--;

                for (Usuario elemento : this.listaUsuarios){
                    if (contador == opcionARevisar){
                        elemento.mostrarDatos();
                        break;
                    } else{
                        contador++;
                    }
                }
            }

        }else {
            System.out.println("----------------------------------------------------------------");
            System.out.println("No hay usuarios registrados...");
            System.out.println("----------------------------------------------------------------");
        }

    }

    /**
     * Metodo encargado de mostrar solo los nombres de los usuarios.
     * Recorre la lista de usuarios y muestra sus alias
     */
    public void mostrarNombres(){
        if (getTamanoLista() > 0){
            int contador = 1;
            for (Usuario elemento : this.listaUsuarios){
                System.out.println(  contador + "." + elemento.getAlias());
                contador++;
            }
            System.out.println();
        }else{
            System.out.println("----------------------------------------------------------------");
            System.out.println("No hay usuarios registrados...");
            System.out.println("----------------------------------------------------------------");
        }

    }
}
