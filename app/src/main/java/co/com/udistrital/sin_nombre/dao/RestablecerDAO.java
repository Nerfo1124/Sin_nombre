package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.util.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.ReestablecerVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla
 * RESTABLECER.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class RestablecerDAO {

    public static String TABLE_NAME = "RESTABLECER";
    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public RestablecerDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "[RestablecerDAO] Error en RestablecerDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<ReestablecerVO> list() {
        try {
            List<ReestablecerVO> listaRes = new ArrayList<ReestablecerVO>();
            String columns[] = {dbh.RESTABLECER_ID, dbh.RESTABLECER_QUEST1, dbh.RESTABLECER_ANSW1,
                                dbh.RESTABLECER_QUEST2, dbh.RESTABLECER_ANSW2,dbh.RESTABLECER_TAMANO_FUENTE};
            Cursor fila = db.query(TABLE_NAME,columns,null,null,null,null,null);
            if(fila.moveToFirst()){
                do{
                    ReestablecerVO vo = new ReestablecerVO();
                    vo.setIdReestablecer(fila.getInt(0));
                    vo.setPregunta1(fila.getString(1));
                    vo.setRespuesta1(fila.getString(2));
                    vo.setPregunta2(fila.getString(3));
                    vo.setRespuesta2(fila.getString(4));
                    vo.setIdReestablecer(fila.getInt(5));
                    listaRes.add(vo);
                } while(fila.moveToNext());
            }
            return listaRes;
        } catch (Exception e){
            Toast.makeText(contexto, "[list] Error en RestablecerDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ReestablecerVO consult(int idRestablecer) {
        ReestablecerVO objRes = new ReestablecerVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(TABLE_NAME);
            sb.append(" WHERE for_id = ").append(idRestablecer);
            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objRes.setIdReestablecer(fila.getInt(0));
                objRes.setPregunta1(fila.getString(1));
                objRes.setRespuesta1(fila.getString(2));
                objRes.setPregunta2(fila.getString(3));
                objRes.setRespuesta2(fila.getString(4));
                objRes.setIdReestablecer(fila.getInt(5));
            }
            return objRes;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en FormulaDAO - consult: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    public boolean insert(ReestablecerVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES (");
            sb.append(vo.getPregunta1() + ",").append(vo.getRespuesta1()).append(",");
            sb.append(vo.getPregunta2() + ",").append(vo.getRespuesta2()).append(",");
            sb.append(vo.getTamanoFuente());
            sb.append(")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    public boolean update(ReestablecerVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(TABLE_NAME).append(" SET ");
            sb.append(dbh.RESTABLECER_QUEST1).append("=").append(vo.getPregunta1()).append(",");
            sb.append(dbh.RESTABLECER_ANSW1).append("=").append(vo.getRespuesta1()).append(",");
            sb.append(dbh.RESTABLECER_QUEST2).append("=").append(vo.getPregunta2()).append(",");
            sb.append(dbh.RESTABLECER_ANSW2).append("=").append(vo.getRespuesta2()).append(",");
            sb.append(dbh.RESTABLECER_TAMANO_FUENTE).append("=").append(vo.getTamanoFuente()).append(" ");
            sb.append("WHERE ").append(dbh.RESTABLECER_ID).append("=").append(vo.getIdReestablecer());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en FormulaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int idRestablecer) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM").append(TABLE_NAME);
            sb.append(" WHERE ").append(dbh.RESTABLECER_ID).append(" = ").append(idRestablecer);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en RestablecerDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
