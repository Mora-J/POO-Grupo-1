import java.io.IOException;

/**
 * La clase Controlador maneja la interacción del jugador con el tablero, capturando la entrada
 * y moviendo al jugador por el tablero.
 *
 * @param tablero el tablero del juego.
 */
public record Controlador(Tablero tablero) {

    /**
     * Captura la entrada del usuario desde el teclado.
     * Este método continúa capturando entrada hasta que se recibe un carácter que no sea nueva línea o retorno de carro.
     *
     * @return el código de la tecla presionada.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public int capturarEntrada() throws IOException {
        int key = System.in.read();

        if (key == '\n' || key == '\r') {
            return capturarEntrada();
        }
        return key;
    }

    /**
     * Actualiza la posición del jugador en el tablero basada en la entrada del usuario.
     *
     * @param i la posición actual del jugador en el eje i (fila).
     * @param j la posición actual del jugador en el eje j (columna).
     * @param key el código de la tecla presionada.
     * @return un arreglo de dos enteros que representan la nueva posición del jugador [i, j].
     */
    private int[] actualizarPosicion(int i, int j, int key) {
        if ((key == 'w' || key == 'W') && i > 0) {
            i--;
        } else if ((key == 's' || key == 'S') && i < tablero.getTablero().length - 1) {
            i++;
        } else if ((key == 'a' || key == 'A') && j > 0) {
            j--;
        } else if ((key == 'd' || key == 'D') && j < tablero.getTablero()[0].length - 1) {
            j++;
        }
        return new int[]{i, j};
    }

    /**
     * Mueve al jugador a una nueva posición en el tablero, resaltando la nueva posición
     * y desresaltando la anterior.
     *
     * @param i la posición actual del jugador en el eje i (fila).
     * @param j la posición actual del jugador en el eje j (columna).
     * @param key el código de la tecla presionada.
     * @return un arreglo de dos enteros que representan la nueva posición del jugador [i, j].
     */
    public int[] moverJugador(int i, int j, int key) {
        int[] nuevaPosicion = actualizarPosicion(i, j, key);

        if (i != nuevaPosicion[0] || j != nuevaPosicion[1]) {
            this.tablero.resaltarPosicion(nuevaPosicion[0], nuevaPosicion[1]);
            this.tablero.desresaltarPosicion(i, j);
        }

        return nuevaPosicion;
    }
}
