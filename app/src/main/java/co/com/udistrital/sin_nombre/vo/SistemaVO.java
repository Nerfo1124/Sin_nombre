package co.com.udistrital.sin_nombre.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SistemaVO {

    private int tamanoFuente;
    private int frecuencia;

    // Bloque Getter
    public int getTamanoFuente() {
        return tamanoFuente;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    // Bloque Setter
    public void setTamanoFuente(int tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
