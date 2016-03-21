package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.udistrital.sin_nombre.dao.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;

/**
 * Created by Fernando on 07/03/2016.
 */
public class HistoricoDAO {

    private static String TAG_LOG = "[Sin_nombre]";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public HistoricoDAO(Context context) {
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e) {
            //Toast.makeText(context, "[HistoricoDAO] Error en HistoricoDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[HistoricoDAO] Error en HistoricoDAO: " + e.toString(), e);
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla FORMULA.
     *
     * @return
     */
    public List<HistoricoVO> list() {
        try {
            List<HistoricoVO> listHistorico = new ArrayList<HistoricoVO>();
            String columns[] = {dbh.HISTORICO_ID, dbh.HISTORICO_ID_USUARIO, dbh.HISTORICO_TIEMPO, dbh.HISTORICO_FECHA};
            Cursor listFormulas = db.query(dbh.TABLE_NAME_HISTORICO, columns, null, null, null, null, null);
            if (listFormulas.moveToFirst()) {
                do {
                    HistoricoVO vo = new HistoricoVO();
                    vo.setId(listFormulas.getInt(0));
                    vo.setIdUsuario(listFormulas.getInt(1));
                    vo.setTiempo(listFormulas.getString(2));
                    Date fechaDate = sdf.parse(listFormulas.getString(3));
                    vo.setFechaHistorico(fechaDate);
                    listHistorico.add(vo);
                } while (listFormulas.moveToNext());
            }
            return listHistorico;
        } catch (Exception e) {
            //Toast.makeText(contexto, "[list] Error en HistoricoDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[list] Error en HistoricoDAO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * FORMULA de la BDD recibiendo como parametro el id del Registro.
     *
     * @param idFormula
     * @return
     */
    public HistoricoVO consult(int idFormula) {
        HistoricoVO objHistorico = new HistoricoVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_HISTORICO);
            sb.append(" WHERE his_id = ").append(idFormula);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null) {
                objHistorico.setId(fila.getInt(0));
                objHistorico.setIdUsuario(fila.getInt(1));
                objHistorico.setTiempo(fila.getString(2));
                objHistorico.setFechaHistorico(sdf.parse(fila.getString(3)));
            }
            return objHistorico;
        } catch (Exception e) {
            //Toast.makeText(contexto, "[consult] Error en HistoricoDAO - consult: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[consult] Error en HistoricoDAO - consult: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de insertar datos en la tabla de FORMULA en la BDD.
     *
     * @param vo
     * @return
     */
    public boolean insert(HistoricoVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_HISTORICO).append("(");
            sb.append(dbh.HISTORICO_ID_USUARIO + " , ").append(dbh.HISTORICO_TIEMPO + " , ").append(dbh.HISTORICO_FECHA).append(")");
            sb.append(" VALUES (");
            sb.append(vo.getIdUsuario() + ",'").append(vo.getTiempo() + "','").append(sdf.format(vo.getFechaHistorico()));
            sb.append("')");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            //Toast.makeText(contexto, "[insert] Error en HistoricoDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[insert] Error en HistoricoDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla FORMULA recibiendo
     * como parametro un objeto con los valores a actualizar.
     *
     * @param vo
     * @return
     */
    public boolean update(HistoricoVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_HISTORICO).append(" SET ");
            sb.append(dbh.HISTORICO_ID).append("=").append(vo.getId()).append(",");
            sb.append(dbh.HISTORICO_ID_USUARIO).append("=").append(vo.getIdUsuario()).append(",");
            sb.append(dbh.HISTORICO_TIEMPO).append("='").append(vo.getTiempo()).append("',");
            sb.append(dbh.HISTORICO_FECHA).append("='").append(vo.getFechaHistorico()).append("' ");
            sb.append("WHERE ").append(dbh.HISTORICO_ID).append("=").append(vo.getId());

            Log.d(TAG_LOG, "SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            //Toast.makeText(contexto, "[update] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[update] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla FORMULA
     *
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
            //Toast.makeText(contexto, "[delete] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[delete] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de devolver el id del ultimo registro en BDD.
     *
     * @return
     */
    public int consultLastID() {
        int response = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT MAX(" + dbh.FORMULA_ID + ") FROM " + dbh.TABLE_NAME_FORMULA);

            Log.d(TAG_LOG, "[consultLastID] SQL: " + sb.toString());

            Cursor id = db.rawQuery(sb.toString(), null);
            if (id.moveToFirst()) {
                response = id.getInt(0);
            }
        } catch (Exception ex) {
            Log.e(TAG_LOG, "[consultLastID] Error durante la ejecucion del metodo.");
        }
        return response;
    }
}
