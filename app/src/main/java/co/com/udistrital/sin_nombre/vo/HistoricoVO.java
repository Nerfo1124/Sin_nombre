package co.com.udistrital.sin_nombre.vo;

import java.util.Date;

/**
 * Created by Fernando on 07/03/2016.
 */
public class HistoricoVO {

    private int id;
    private int tiempo;
    private Date fechaHistorico;

    public HistoricoVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Date getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(Date fechaHistorico) {
        this.fechaHistorico = fechaHistorico;
    }
}
