package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
    public DataBaseHelper helper;
    public SQLiteDatabase db;
    public Context contexto;

    public FormulaDAO(Context context){
        try {
            contexto = context;
            helper = new DataBaseHelper(context);
            db = helper.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "Error en FormulaDAO - consult: " + e.toString(), Toast.LENGTH_SHORT ).show();
        }
    }

    public void list() {

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
            fila.close();
            return objFormula;
        } catch (Exception e){
            Toast.makeText(contexto, "Error en FormulaDAO - consult: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    // TODO pendiente confirmacion de la consulta SQL.
    public boolean insert(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES (");
            sb.append(vo.getIdFormula()).append("," + vo.getaVisualOD()+",");
            sb.append(vo.getaVisualOI()+")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "Error en FormulaDAO - insert: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    public boolean update(int idFormula) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(TABLE_NAME).append(" SET ");
            sb.append("for_id = ").append(idFormula);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "Error en FormulaDAO - update " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int idFormula) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM").append(TABLE_NAME);
            sb.append(" WHERE for_id = ").append(idFormula);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "Error en FormulaDAO - delete " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
