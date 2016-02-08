package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.dao.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.FormulaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla FORMULA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class FormulaDAO {

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
            System.out.println("[FormulaDAO] Error en FormulaDAO: " + e.toString());
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla FORMULA.
     * @return
     */
    public List<FormulaVO> list() {
        try {
            List<FormulaVO> listaFormula = new ArrayList<FormulaVO>();
            String columns[] = {dbh.FORMULA_ID, dbh.FORMULA_OJO_DER, dbh.FORMULA_OJO_IZQ};
            Cursor listFormulas = db.query(dbh.TABLE_NAME_FORMULA,columns,null,null,null,null,null);
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
            System.out.println("[list] Error en FormulaDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un registro en la tabla
     * FORMULA de la BDD recibiendo como parametro el id del registro.
     * @param idFormula
     * @return
     */
    public FormulaVO consult(int idFormula) {
        FormulaVO objFormula = new FormulaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_FORMULA);
            sb.append(" WHERE for_id = ").append(idFormula);

            System.out.println("SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objFormula.setIdFormula(fila.getInt(0));
                objFormula.setaVisualOD(fila.getString(1));
                objFormula.setaVisualOI(fila.getString(2));
            }
            return objFormula;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en FormulaDAO - consult: " + e.toString(), Toast.LENGTH_SHORT ).show();
            System.out.println("[consult] Error en FormulaDAO - consult: " + e.toString());
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
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_FORMULA).append("(");
            sb.append(dbh.FORMULA_OJO_DER).append(dbh.FORMULA_OJO_IZQ).append(")");
            sb.append(" VALUES ('");
            sb.append(vo.getaVisualOD() + "','").append(vo.getaVisualOI());
            sb.append("')");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            System.out.println("[insert] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un registro de la tabla FORMULA recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_FORMULA).append(" SET ");
            sb.append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula()).append(",");
            sb.append(dbh.FORMULA_OJO_DER).append("='").append(vo.getaVisualOD()).append("',");
            sb.append(dbh.FORMULA_OJO_IZQ).append("='").append(vo.getaVisualOI()).append("' ");
            sb.append("WHERE ").append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula());

            System.out.println("SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("[update] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un registro en la tabla FORMULA
     * @param idFormula
     * @return
     */
    public boolean delete(int idFormula) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_FORMULA);
            sb.append(" WHERE ").append(dbh.FORMULA_ID).append(" = ").append(idFormula);

            System.out.println("SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("[delete] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }
}
