import com.google.gson.annotations.SerializedName;

/**
 * Clase Jugador.
 * Representa a un jugador en el juego, con sus atributos y métodos para interactuar con las fichas y el tablero.
 */
public class Jugador {
    private String alias;
    private int scoreInGame;
    private String tiempoJugado;
    private int cantidadPalabrasColocadas = 0;
    private final Ficha[] fichas;
    private int horasJugadasInGame;
    private int minutosJugadosInGame;
    private int segundosJugadosInGame;

    //datos de usuario
    @SerializedName("correo electronico")
    private String correoElectronico;

    @SerializedName("score total")
    private int scoreTotal;
    private int horasJugadas;
    private int minutosJugados;
    private int segundosJugados;
    private int cantidadDePalabras;

    /**
     * Constructor de la clase Jugador.
     * @param alias El alias del jugador.
     */
    public Jugador(String alias,String correoElectronico, int scoreTotal, int horasJugadas, int minutosJugados, int segundosJugados, int cantidadDePalabras) {
        this.alias = alias;
        this.fichas = new Ficha[7];
        this.correoElectronico = correoElectronico;
        this.scoreInGame = 0;
        this.tiempoJugado = "0h 0m 0s";

        //datos de usuario
        this.scoreTotal = scoreTotal;
        this.horasJugadas = horasJugadas;
        this.minutosJugados = minutosJugados;
        this.segundosJugados = segundosJugados;
        this.cantidadDePalabras = cantidadDePalabras;


    }
    /**
     * Constructor vacio de la clase Jugador.
     * Solo iniciliza las fichas.
     */
    public Jugador() {
        this.fichas = new Ficha[7];
    }

    /**
     * Obtiene las fichas del jugador.
     * @return Un array de Ficha que contiene las fichas del jugador.
     */
    public Ficha[] getFichas() {
        return fichas;
    }

    /**
     * Muestra las fichas del jugador en la consola.
     * Imprime las fichas actuales del jugador, mostrando "__" si no hay ficha en esa posición.
     */
    public void mostrarFichas() {
        StringBuilder atril = new StringBuilder();
        for (Ficha ficha : this.fichas) {
            if (ficha != null) {
                atril.append(ficha);
            } else {
                atril.append(" __ ");
            }
        }
        System.out.println(atril.toString());
    }

    /**
     * Clona las fichas del jugador.
     * @return Un nuevo array de Ficha con copias de las fichas del jugador.
     */
    public Ficha[] clonarFichas() {
        Ficha[] clon = new Ficha[fichas.length];
        for (int i = 0; i < fichas.length; i++) {
            clon[i] = fichas[i].clone();
        }
        return clon;
    }

    /**
     * Juega la primera ficha en el tablero.
     * @param tablero El tablero en el que se coloca la ficha.
     * @param indexFichas El índice de la ficha a colocar.
     * @return true si la ficha se colocó correctamente, false en caso contrario.
     */
    public boolean jugarPrimeraFicha(Tablero tablero, int indexFichas) {
        if (tablero.colocarPrimeraFicha(fichas[indexFichas])) {
            fichas[indexFichas] = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Juega una ficha en el tablero.
     * @param tablero El tablero en el que se coloca la ficha.
     * @param fila La fila en la que se coloca la ficha.
     * @param columna La columna en la que se coloca la ficha.
     * @param indexFichas El índice de la ficha a colocar.
     * @return true si la ficha se colocó correctamente, false en caso contrario.
     */
    public boolean jugarFichas(Tablero tablero, int fila, int columna, int indexFichas) {
        if (tablero.colocarFicha(fila, columna, fichas[indexFichas])) {
            fichas[indexFichas] = null;
            return true;
        } else {
            System.out.println("No puedes colocar una ficha en esta posicion");
            return false;
        }
    }

    /**
     * Rellena las fichas del jugador con fichas de la bolsa.
     * @param bolsaFichas La bolsa de donde se sacarán las fichas.
     */
    public void rellenarFichas(BolsaFichas bolsaFichas) {
        for (int i = 0; i < 7; i++) {
            int randomIndex = (int) (Math.random() * (bolsaFichas.getListaFichas().size() - 1));
            if (fichas[i] == null && !bolsaFichas.getListaFichas().isEmpty()) {
                fichas[i] = bolsaFichas.getListaFichas().get(randomIndex).clone();
                bolsaFichas.getListaFichas().remove(randomIndex);
            }
        }
    }

    /**
     * Verifica si el jugador no tiene fichas.
     * @return true si el jugador no tiene fichas, false en caso contrario.
     */
    public boolean fichasIsEmpty() {
        for(Ficha ficha : fichas) {
            if(ficha != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Establece las fichas del jugador.
     * @param fichas Un array de Ficha que contiene las nuevas fichas del jugador.
     */
    public void setFichas(Ficha[] fichas) {
        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i] != null) {
                this.fichas[i] = fichas[i].clone();
            }
        }
    }

    /**
     * Obtiene el alias del jugador.
     * @return El alias del jugador.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Obtiene la puntuación total del jugador.
     * @return La puntuación total del jugador.
     */
    public int getScore() {
        return scoreInGame;
    }

    /**
     * Agrega puntos a la puntuación total del jugador.
     * @param scoreTotal Los puntos a agregar a la puntuación total.
     */
    public void addToScore(int scoreTotal) {
        this.scoreInGame += scoreTotal;
    }

    /**
     * Obtiene el tiempo jugado por el jugador.
     * @return El tiempo jugado por el jugador.
     */
    public String getTiempoJugado() {
        return tiempoJugado;
    }

    /**
     * Establece el tiempo jugado por el jugador.
     * @param tiempoJugado El tiempo jugado por el jugador.
     */
    public void setTiempoJugado(String tiempoJugado) {
        this.tiempoJugado = tiempoJugado;
    }

    /**
     * Obtiene la cantidad de palabras colocadas por el jugador.
     * @return La cantidad de palabras colocadas por el jugador.
     */
    public int getCantidadPalabrasColocadas() {
        return cantidadPalabrasColocadas;
    }

    /**
     * Agrega palabras a la cantidad de palabras colocadas por el jugador.
     * @param cantidadPalabras La cantidad de palabras a agregar.
     */
    public void addToCantidadPalabrasColocadas(int cantidadPalabras) {
        this.cantidadPalabrasColocadas += cantidadPalabras;
    }

    /**
     * Obtiene el puntaje en el juego.
     *
     * @return El puntaje en el juego.
     */
    public int getScoreInGame() {
        return scoreInGame;
    }

    /**
     * Establece el puntaje en el juego.
     *
     * @param scoreInGame El puntaje en el juego.
     */
    public void setScoreInGame(int scoreInGame) {
        this.scoreInGame = scoreInGame;
    }

    /**
     * Establece la cantidad de palabras colocadas.
     *
     * @param cantidadPalabrasColocadas La cantidad de palabras colocadas.
     */
    public void setCantidadPalabrasColocadas(int cantidadPalabrasColocadas) {
        this.cantidadPalabrasColocadas = cantidadPalabrasColocadas;
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

    /**
     * Obtiene un objeto UsuarioData con los datos del usuario.
     *
     * @return Un objeto UsuarioData con los datos del usuario.
     */
    public UsuarioData getUsuarioData() {
        return new UsuarioData(alias, correoElectronico, scoreTotal, horasJugadas, minutosJugados, segundosJugados, cantidadDePalabras);
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHorasJugadasInGame() {
        return horasJugadasInGame;
    }

    public void setHorasJugadasInGame(int horasJugadasInGame) {
        this.horasJugadasInGame = horasJugadasInGame;
    }

    public int getMinutosJugadosInGame() {
        return minutosJugadosInGame;
    }

    public void setMinutosJugadosInGame(int minutosJugadosInGame) {
        this.minutosJugadosInGame = minutosJugadosInGame;
    }

    public int getSegundosJugadosInGame() {
        return segundosJugadosInGame;
    }

    public void setSegundosJugadosInGame(int segundosJugadosInGame) {
        this.segundosJugadosInGame = segundosJugadosInGame;
    }
}


