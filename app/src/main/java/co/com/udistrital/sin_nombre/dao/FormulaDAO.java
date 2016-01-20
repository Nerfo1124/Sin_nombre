package co.com.udistrital.sin_nombre.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.util.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.FormulaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla FORMULA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class FormulaDAO {

    public static String TABLE_NAME = "FORMULA";
    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public FormulaDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "[FormulaDAO] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
        }
    }

    public List<FormulaVO> list() {
        try {
            List<FormulaVO> listaFormula = new ArrayList<FormulaVO>();
            String columns[] = {dbh.FORMULA_ID, dbh.FORMULA_OJO_DER, dbh.FORMULA_OJO_IZQ};
            Cursor listFormulas = db.query(TABLE_NAME,columns,null,null,null,null,null);
            if(listFormulas.moveToFirst()){
                do{
                    FormulaVO vo = new FormulaVO();
                    vo.setIdFormula(listFormulas.getInt(0));
                    vo.setaVisualOD(listFormulas.getString(1));
                    vo.setaVisualOI(listFormulas.getString(2));
                    listaFormula.add(vo);
                } while(listFormulas.moveToNext());
            }
            return listaFormula;
        } catch (Exception e){
            Toast.makeText(contexto, "[list] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public FormulaVO consult(int idFormula) {
        FormulaVO objFormula = new FormulaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(TABLE_NAME);
            sb.append(" WHERE for_id = ").append(idFormula);
            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                int i = 0;
                objFormula.setIdFormula(fila.getInt(i));
                objFormula.setaVisualOD(fila.getString(i++));
                objFormula.setaVisualOI(fila.getString(i++));
            }
            return objFormula;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en FormulaDAO - consult: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de insertar datos en la tabla de FORMULA en la BDD.
     * @param vo
     * @return
     */
    public boolean insert(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES (");
            sb.append(vo.getaVisualOD() + ",").append(vo.getaVisualOI());
            sb.append(")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    public boolean update(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(TABLE_NAME).append(" SET ");
            sb.append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula()).append(",");
            sb.append(dbh.FORMULA_OJO_DER).append("=").append(vo.getaVisualOD()).append(",");
            sb.append(dbh.FORMULA_OJO_IZQ).append("=").append(vo.getaVisualOI()).append(" ");
            sb.append("WHERE ").append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int idFormula) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM").append(TABLE_NAME);
            sb.append(" WHERE ").append(dbh.FORMULA_ID).append(" = ").append(idFormula);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
