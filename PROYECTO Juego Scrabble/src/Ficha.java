/**
 * La clase Ficha representa una fecha de juego con un valor, una letra y un simbolo.
 * Esta clase implementa la interfaz {@code Cloneable}, lo que permite clonar objetos de esta clase.
 */

public class Ficha implements Cloneable {
    /**
     * Valor de la ficha.
     */

    private int valor;
    /**
     * Letra representada en la ficha.
     */
    private String letra;
    /**
     * Simbolo de la ficha, generado a partir de la letra.
     */
    private String simbolo;

    /**
     * Constructor que inicializa el valor y la letra de la ficha.
     * Genera el simbolo correspondiente.
     * @param valor el valor de la ficha
     * @param letra la letra representada en la ficha
     */
    public Ficha(int valor, String letra) {
        this.valor = valor;
        this.letra = letra;

        if (letra.length() == 1){
            this.simbolo = "\033[1;30;107m["+letra+" ]\033[0m";
        }else {
            this.simbolo = "\033[1;30;107m[" + letra + "]\033[0m";
        }
    }

    /**
     * Constructor por defecto que inicializa la ficha con un valor de 0 y una letra vacia
     */
    public Ficha() {
        this.valor = 0;
        this.letra = "  ";
        this.simbolo = "["+letra+"]";
    }

    /**
     * Obtiene el simbolo de la ficha.
     *
     * @return el simbolo de la ficha.
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Establece la letra de la ficha y actualiza el simbolo correspondiente.
     * @param letra la nueva letra de la ficha
     */
    public void setLetra(String letra) {
        this.letra = letra;
        if (letra.length() == 1){
            this.simbolo = "\033[1;30;107m["+letra+" ]\033[0m";
        }else {
            this.simbolo = "\033[1;30;107m[" + letra + "]\033[0m";
        }
    }

    /**
     * Crea y devuelve una copia de esta ficha.
     * Implementa el metodo clone() de la interfaz {@code Cloneable}.
     *
     * @return una copia de esta ficha.
     */
    @Override
    public Ficha clone() {
        try {
            return (Ficha) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage() + " - Se jodio el programa, te debo un helado, Joselito :c");
            return null;
        }
    }

    /**
     * Obtiene la letra de la ficha.
     *
     * @return la letra de la ficha.
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Establece el símbolo de la ficha.
     *
     * @param simbolo el nuevo símbolo de la ficha.
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * Establece el valor de la ficha.
     *
     * @param valor el nuevo valor de la ficha.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el valor de la ficha.
     *
     * @return el valor de la ficha.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Devuelve una representación en forma de cadena del símbolo de la ficha.
     *
     * @return la representación en cadena del símbolo de la ficha.
     */
    @Override
    public String toString() {
        return simbolo;
    }
}