import java.util.Scanner;
import java.util.Arrays;

/**
 * La clase Tablero representa el tablero de juego que contiene fichas organizadas en una cuadrícula.
 */
public class Tablero {

    /**
     * Matriz de fichas que representa el tablero.
     */
    private final Ficha[][] tablero;

    /**
     * Fila central del tablero.
     */
    private int filaCentral;

    /**
     * Columna central del tablero.
     */
    private int columnaCentral;

    /**
     * Obtiene la matriz de fichas que representa el tablero.
     *
     * @return la matriz de fichas del tablero.
     */
    public Ficha[][] getTablero() {
        return tablero;
    }

    /**
     * Resalta la posición especificada en el tablero.
     *
     * @param fila la fila de la posición a resaltar.
     * @param columna la columna de la posición a resaltar.
     */
    public void resaltarPosicion(int fila, int columna) {
        Ficha ficha = tablero[fila][columna];
        String letra = ficha.getLetra();
        if (letra.length() == 1) {
            ficha.setSimbolo("\033[30;43m[" + letra + " ]\033[0m");
        } else {
            ficha.setSimbolo("\033[30;43m[" + letra + "]\033[0m");
        }
    }

    /**
     * Quita el resaltado de la posición especificada en el tablero.
     *
     * @param fila la fila de la posición a quitar el resaltado.
     * @param columna la columna de la posición a quitar el resaltado.
     */
    public void desresaltarPosicion(int fila, int columna) {
        String simbolo = tablero[fila][columna].getSimbolo();
        String simboloSinANSI = simbolo.replaceAll("\033\\[[;\\d]*m", "");
        tablero[fila][columna].setSimbolo(simboloSinANSI);

        if (!tablero[fila][columna].getLetra().equals("  ")) {
            simbolo = "\033[1;30;107m" + simboloSinANSI + "\033[0m";
            tablero[fila][columna].setSimbolo(simbolo);
        }
    }

    /**
     * Constructor que inicializa el tablero con un tamaño de 15x15 y ficha vacías.
     */
    public Tablero() {
        this.tablero = new Ficha[15][15];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                this.tablero[i][j] = new Ficha();
            }
        }
        this.filaCentral = (tablero.length - 1) / 2;
        this.columnaCentral = (tablero[0].length - 1) / 2;
    }

    /**
     * Constructor de copia que crea una copia del tablero original.
     *
     * @param original el tablero original a copiar.
     */
    public Tablero(Tablero original) {
        this.tablero = new Ficha[original.getTablero().length][original.getTablero()[0].length];
        for (int i = 0; i < original.getTablero().length; i++) {
            for (int j = 0; j < original.getTablero().length; j++) {
                this.tablero[i][j] = original.getTablero()[i][j].clone();
            }
        }
        this.filaCentral = original.getFilaCentral();
        this.columnaCentral = original.getColumnaCentral();
    }

    /**
     * Valida si una posición en el tablero está disponible para colocar una ficha.
     *
     * @param fila la fila de la posición a validar.
     * @param columna la columna de la posición a validar.
     * @return {@code true} si la posición está disponible, {@code false} en caso contrario.
     */
    public boolean validarFichaPosicion(int fila, int columna) {
        if (fila >= 0 && fila < tablero.length && columna >= 0 && columna < tablero.length) {
            return tablero[fila][columna].getLetra().equals("  ");
        }
        return false;
    }

    /**
     * Asigna una letra a una ficha comodín.
     *
     * @param ficha la ficha comodín a asignar.
     * @return {@code true} si se asignó una letra válida, {@code false} en caso contrario.
     */
    private boolean asignarComodin(Ficha ficha) {
        String[] fichasValidas = new String[]{"A", "B", "C", "CH", "D", "E", "F", "G", "H", "I",
                "J", "L", "LL", "M", "N", "Ñ", "O", "P", "Q", "R", "RR", "S", "T", "U", "V", "X", "Y", "Z"};

        Scanner scanner = new Scanner(System.in);
        boolean letraValida = false;

        if (ficha.getLetra().equals("#")) {
            while (!letraValida) {
                System.out.println("Ingrese en que ficha quiere convertir su comodín: (recuerde que también están LL, CH, RR)");
                System.out.println("Ingrese 9 para cancelar");
                String fichaIngresada = scanner.nextLine().toUpperCase().trim();
                if (fichaIngresada.equals("9")) {
                    return false;
                }
                if (esFichaValida(fichaIngresada, fichasValidas)) {
                    System.out.println("La ficha " + fichaIngresada + " es válida.");
                    ficha.setLetra(fichaIngresada);
                    letraValida = true;
                } else {
                    System.out.println("La ficha " + fichaIngresada + " no es válida. Inténtelo nuevamente.");
                }
            }
            return true;
        }
        return true;
    }

    /**
     * Verifica si una ficha es válida.
     *
     * @param ficha la ficha a verificar.
     * @param fichasValidas el arreglo de fichas válidas.
     * @return {@code true} si la ficha es válida, {@code false} en caso contrario.
     */
    private static boolean esFichaValida(String ficha, String[] fichasValidas) {
        return Arrays.asList(fichasValidas).contains(ficha);
    }

    /**
     * Coloca la primera ficha en el tablero en la posición central.
     *
     * @param ficha la ficha a colocar.
     * @return {@code true} si la ficha se colocó con éxito, {@code false} en caso contrario.
     */
    public boolean colocarPrimeraFicha(Ficha ficha) {
        if (validarFichaPosicion(7, 7) && ficha != null && asignarComodin(ficha)) {
            this.tablero[7][7] = ficha;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Coloca una ficha en una posición específica del tablero.
     *
     * @param fila la fila donde se va a colocar la ficha.
     * @param columna la columna donde se va a colocar la ficha.
     * @param ficha la ficha a colocar.
     * @return {@code true} si la ficha se colocó con éxito, {@code false} en caso contrario.
     */
    public boolean colocarFicha(int fila, int columna, Ficha ficha) {
        boolean hayFichaAdyacente = (fila > 0 && !tablero[fila - 1][columna].getLetra().equals("  ")) ||
                (fila < tablero.length - 1 && !tablero[fila + 1][columna].getLetra().equals("  ")) ||
                (columna > 0 && !tablero[fila][columna - 1].getLetra().equals("  ")) ||
                (columna < tablero[0].length - 1 && !tablero[fila][columna + 1].getLetra().equals("  "));

        if (validarFichaPosicion(fila, columna) && hayFichaAdyacente && ficha != null && asignarComodin(ficha)) {
            this.tablero[fila][columna] = ficha;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Muestra el tablero en la consola.
     */
    public void mostrarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            System.out.print("\t");
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j]);
            }
            System.out.print("\n");
        }
    }

    /**
     * Obtiene la fila central del tablero.
     *
     * @return la fila central del tablero.
     */
    public int getFilaCentral() {
        return filaCentral;
    }

    /**
     * Establece la fila central del tablero.
     *
     * @param filaCentral la nueva fila central del tablero.
     */
    public void setFilaCentral(int filaCentral) {
        this.filaCentral = filaCentral;
    }

    /**
     * Obtiene la columna central del tablero.
     *
     * @return la columna central del tablero.
     */
    public int getColumnaCentral() {
        return columnaCentral;
    }
}

