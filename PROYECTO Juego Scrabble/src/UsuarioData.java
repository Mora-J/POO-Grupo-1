import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa los datos de un usuario.
 */
public class UsuarioData {
    private String alias;

    @SerializedName("correo electronico")
    private String correoElectronico;

    @SerializedName("score total")
    private int scoreTotal;

    private int horasJugadas;
    private int minutosJugados;
    private int segundosJugados;
    private int cantidadDePalabras;

    /**
     * Constructor de la clase UsuarioData.
     *
     * @param alias              El alias del usuario.
     * @param correoElectronico  El correo electrónico del usuario.
     * @param scoreTotal         El puntaje total del usuario.
     * @param horasJugadas       Las horas jugadas por el usuario.
     * @param minutosJugados     Los minutos jugados por el usuario.
     * @param segundosJugados    Los segundos jugados por el usuario.
     * @param cantidadDePalabras La cantidad de palabras jugadas por el usuario.
     */
    public UsuarioData(String alias, String correoElectronico, int scoreTotal, int horasJugadas, int minutosJugados, int segundosJugados, int cantidadDePalabras) {
        this.alias = alias;
        this.correoElectronico = correoElectronico;
        this.scoreTotal = scoreTotal;
        this.horasJugadas = horasJugadas;
        this.minutosJugados = minutosJugados;
        this.segundosJugados = segundosJugados;
        this.cantidadDePalabras = cantidadDePalabras;
    }

    /**
     * Obtiene el alias del usuario.
     *
     * @return El alias del usuario.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Establece el alias del usuario.
     *
     * @param alias El alias del usuario.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correoElectronico El correo electrónico del usuario.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Obtiene el puntaje total del usuario.
     *
     * @return El puntaje total del usuario.
     */
    public int getScoreTotal() {
        return scoreTotal;
    }

    /**
     * Establece el puntaje total del usuario.
     *
     * @param scoreTotal El puntaje total del usuario.
     */
    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    /**
     * Obtiene las horas jugadas por el usuario.
     *
     * @return Las horas jugadas por el usuario.
     */
    public int getHorasJugadas() {
        return horasJugadas;
    }

    /**
     * Establece las horas jugadas por el usuario.
     *
     * @param horasJugadas Las horas jugadas por el usuario.
     */
    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    /**
     * Obtiene los minutos jugados por el usuario.
     *
     * @return Los minutos jugados por el usuario.
     */
    public int getMinutosJugados() {
        return minutosJugados;
    }

    /**
     * Establece los minutos jugados por el usuario.
     *
     * @param minutosJugados Los minutos jugados por el usuario.
     */
    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    /**
     * Obtiene los segundos jugados por el usuario.
     *
     * @return Los segundos jugados por el usuario.
     */
    public int getSegundosJugados() {
        return segundosJugados;
    }

    /**
     * Establece los segundos jugados por el usuario.
     *
     * @param segundosJugados Los segundos jugados por el usuario.
     */
    public void setSegundosJugados(int segundosJugados) {
        this.segundosJugados = segundosJugados;
    }

    /**
     * Obtiene la cantidad de palabras jugadas por el usuario.
     *
     * @return La cantidad de palabras jugadas por el usuario.
     */
    public int getCantidadDePalabras() {
        return cantidadDePalabras;
    }

    /**
     * Establece la cantidad de palabras jugadas por el usuario.
     *
     * @param cantidadDePalabras La cantidad de palabras jugadas por el usuario.
     */
    public void setCantidadDePalabras(int cantidadDePalabras) {
        this.cantidadDePalabras = cantidadDePalabras;
    }
}
