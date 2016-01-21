package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.dao.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.SistemaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla SISTEMA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class SistemaDAO {

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

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla SISTEMA.
     * @return
     */
    public List<SistemaVO> list() {
        try {
            List<SistemaVO> listaSistema = new ArrayList<SistemaVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SISTEMA);
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

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un registro en la tabla
     * SISTEMA de la BDD recibiendo como parametro el id del registro.
     * @param idSistema
     * @return
     */
    public SistemaVO consult(int idSistema) {
        SistemaVO objSistema = new SistemaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SISTEMA);
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

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de SISTEMA
     * @param vo
     * @return
     */
    public boolean insert(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_SISTEMA).append("(");
            sb.append(dbh.SISTEMA_TAM_FUENTE+",").append(dbh.SISTEMA_FRECUENCIA+")");
            sb.append(" VALUES (");
            sb.append(vo.getTamanoFuente() + ",").append(vo.getFrecuencia());
            sb.append(")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un registro de la tabla SISTEMA recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_SISTEMA).append(" SET ");
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

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un registro en la tabla SISTEMA
     * recibiendo como parametro el id del registro a eliminar.
     * @param idSistema
     * @return
     */
    public boolean delete(int idSistema) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_SISTEMA);
            sb.append(" WHERE ").append(dbh.SISTEMA_ID).append(" = ").append(idSistema);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en SistemaDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
