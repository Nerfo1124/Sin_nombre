package co.com.udistrital.sin_nombre.vo;

import java.util.Date;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class FormulaVO {

    private int idFormula;
    private String aVisualOD;
    private String aVisualOI;

    // Bloque Getter
    public int getIdFormula() {
        return idFormula;
    }

    public String getaVisualOD() {
        return aVisualOD;
    }

    public String getaVisualOI() {
        return aVisualOI;
    }

    // Bloque Setter
    public void setIdFormula(int idFormula) {
        this.idFormula = idFormula;
    }

    public void setaVisualOD(String aVisualOD) {
        this.aVisualOD = aVisualOD;
    }

    public void setaVisualOI(String aVisualOI) {
        this.aVisualOI = aVisualOI;
    }
}
