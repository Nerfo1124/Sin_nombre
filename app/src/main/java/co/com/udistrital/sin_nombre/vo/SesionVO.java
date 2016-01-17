package co.com.udistrital.sin_nombre.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SesionVO {

    private int idSesion;
    private int tamanoFuente;
    private int frecuencia;

    SesionVO(){};

    // Bloque Getter
    public int getIdSesion() {
        return idSesion;
    }

    public int getTamanoFuente() {
        return tamanoFuente;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    //Bloque Setter
    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public void setTamanoFuente(int tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}


