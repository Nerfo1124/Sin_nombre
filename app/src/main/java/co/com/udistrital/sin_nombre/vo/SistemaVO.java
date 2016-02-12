package co.com.udistrital.sin_nombre.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SistemaVO {

    private int idSistema;
    private float tamanoFuente;
    private float frecuencia;

    // Bloque Getter
    public int getIdSistema() {
        return idSistema;
    }

    public float getTamanoFuente() {
        return tamanoFuente;
    }

    public float getFrecuencia() {
        return frecuencia;
    }

    // Bloque Setter
    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }

    public void setTamanoFuente(float tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }
}
