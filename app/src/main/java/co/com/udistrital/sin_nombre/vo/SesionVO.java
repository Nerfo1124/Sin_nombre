package co.com.udistrital.sin_nombre.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SesionVO {

    private int idSesion;
    private int usuario;
    private int contrasena;

    public SesionVO(){};

    // Bloque Getter

    public int getIdSesion() {
        return idSesion;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getContrasena() {
        return contrasena;
    }

    //Bloque Setter

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(int contrasena) {
        this.contrasena = contrasena;
    }
}


