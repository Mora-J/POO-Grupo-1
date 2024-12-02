/**
 * La clase Usuario representa un usuario con diversos atributos asociados a sus estadísticas de juego.
 */
public class Usuario {

    private String alias;
    private String correoElectronico;
    private int scoreTotal;
    private int horasJugadas;
    private int minutosJugados;
    private int segundosJugados;
    private int cantidadDePalabras;

    /**
     * Constructor que inicializa un nuevo usuario con alias y correo electrónico.
     * Establece las estadísticas de juego a cero.
     *
     * @param alias el alias del usuario
     * @param correo el correo electrónico del usuario
     */
    public Usuario(String alias, String correo) {
        this.alias = alias;
        this.correoElectronico = correo;
        this.scoreTotal = 0;
        this.horasJugadas = 0;
        this.minutosJugados = 0;
        this.segundosJugados = 0;
        this.cantidadDePalabras = 0;
    }

    /**
     * Constructor que inicializa un nuevo usuario con alias, correo electrónico y estadísticas de juego.
     *
     * @param alias el alias del usuario
     * @param correo el correo electrónico del usuario
     * @param puntosTotales los puntos totales acumulados por el usuario
     * @param minutosJugados los minutos jugados por el usuario
     * @param horasJugadas las horas jugadas por el usuario
     * @param segundosJugados los segundos jugados por el usuario
     * @param cantidadDePalabras la cantidad de palabras colocadas por el usuario
     */
    public Usuario(String alias, String correo, int puntosTotales, int minutosJugados, int horasJugadas, int segundosJugados, int cantidadDePalabras) {
        this.alias = alias;
        this.correoElectronico = correo;
        this.scoreTotal = puntosTotales;
        this.minutosJugados = minutosJugados;
        this.horasJugadas = horasJugadas;
        this.segundosJugados = segundosJugados;
        this.cantidadDePalabras = cantidadDePalabras;
    }

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    // Métodos getter y setter para cada atributo

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correo) {
        this.correoElectronico = correo;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public int getSegundosJugados() {
        return segundosJugados;
    }

    public void setSegundosJugados(int segundosJugados) {
        this.segundosJugados = segundosJugados;
    }

    public int getCantidadDePalabras() {
        return cantidadDePalabras;
    }

    public void setCantidadDePalabras(int cantidadDePalabras) {
        this.cantidadDePalabras = cantidadDePalabras;
    }

    /**
     * Muestra los datos del usuario en la consola.
     */
    public void mostrarDatos() {
        System.out.println("- Alias: " + this.alias);
        System.out.println("- Correo electronico: " + this.correoElectronico);
        System.out.println("- Score total: " + this.scoreTotal);
        System.out.println("- Tiempo jugado: " + this.horasJugadas + ":" + this.minutosJugados + ":" + this.segundosJugados);
        System.out.println("- Total de palabras colocadas: " + this.cantidadDePalabras);
    }
}



