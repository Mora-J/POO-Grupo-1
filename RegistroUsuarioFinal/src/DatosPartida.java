/**
 * Esta clase representa los datos de una partida y proporciona un método para mostrarlos.
 *
 * <p>Implementa la interfaz {@code MostrarDatos} y contiene varios atributos relacionados con la partida,
 * como alias del jugador, nombre del ganador, puntaje total y tiempo jugado.</p>
 */
public class DatosPartida implements MostrarDatos {
    private String alias;
    private String ganador;
    private int scoreTotal;
    private int horasJugadas;
    private int minutosJugados;
    private int segundosJugados;
    private int palabrasColocadas;

    /**
     * Muestra los datos de la partida en la consola en un formato legible.
     */
    @Override
    public void mostrarDatos() {
        System.out.println("[");
        System.out.println("    Alias: " + this.alias);
        System.out.println("    Resultado: " + this.ganador);
        System.out.println("    Puntos de la partida: " + this.scoreTotal);
        System.out.println("    Tiempo de la partida: " + this.horasJugadas + ":" + this.minutosJugados + ":" + this.segundosJugados);
        System.out.println("    Palabras colocadas: " + this.palabrasColocadas);
        System.out.println("]");
        System.out.println("");
    }
}
