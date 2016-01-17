package co.com.udistrital.sin_nombre.vo;

import java.util.Date;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class FormulaVO {

    private String nombreUsuario;
    private String apellido1Usuario;
    private String apellido2Usuario;
    private Date fechaNacimiento;
    private String sexo;

    // Bloque Getter
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellido1Usuario() {
        return apellido1Usuario;
    }

    public String getApellido2Usuario() {
        return apellido2Usuario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    // Bloque Setter
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setApellido2Usuario(String apellido2Usuario) {
        this.apellido2Usuario = apellido2Usuario;
    }

    public void setApellido1Usuario(String apellido1Usuario) {
        this.apellido1Usuario = apellido1Usuario;
    }
}
