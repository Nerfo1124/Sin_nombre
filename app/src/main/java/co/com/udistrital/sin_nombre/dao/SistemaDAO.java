package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.util.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.SistemaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla SISTEMA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class SistemaDAO {

    public static String TABLE_NAME = "SISTEMA";
    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public SistemaDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "[SistemaDAO] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<SistemaVO> list() {
        try {
            List<SistemaVO> listaSistema = new ArrayList<SistemaVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(TABLE_NAME);
            Cursor listSistemas = db.rawQuery(sb.toString(), null);
            if(listSistemas.moveToFirst()){
                do{
                    SistemaVO vo = new SistemaVO();
                    vo.setIdSistema(listSistemas.getInt(0));
                    vo.setTamanoFuente(listSistemas.getInt(1));
                    vo.setFrecuencia(listSistemas.getInt(2));
                    listaSistema.add(vo);
                } while(listSistemas.moveToNext());
            }
            return listaSistema;
        } catch (Exception e){
            Toast.makeText(contexto, "[list] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public SistemaVO consult(int idSistema) {
        SistemaVO objSistema = new SistemaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(TABLE_NAME);
            sb.append(" WHERE ").append(dbh.SISTEMA_ID).append(" = ").append(idSistema);
            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objSistema.setIdSistema(fila.getInt(0));
                objSistema.setTamanoFuente(fila.getInt(1));
                objSistema.setFrecuencia(fila.getInt(2));
            }
            return objSistema;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    public boolean insert(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES (");
            sb.append(vo.getTamanoFuente() + ",").append(vo.getFrecuencia());
            sb.append(")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    public boolean update(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(TABLE_NAME).append(" SET ");
            sb.append(dbh.SISTEMA_TAM_FUENTE).append("=").append(vo.getTamanoFuente()).append(",");
            sb.append(dbh.SISTEMA_FRECUENCIA).append("=").append(vo.getFrecuencia()).append(" ");
            sb.append("WHERE ").append(dbh.SISTEMA_ID).append("=").append(vo.getIdSistema());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en SistemanDAO " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int idSistema) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM").append(TABLE_NAME);
            sb.append(" WHERE ").append(dbh.SISTEMA_ID).append(" = ").append(idSistema);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
