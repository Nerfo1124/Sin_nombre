package co.com.udistrital.sin_nombre.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.sin_nombre.dao.database.DataBaseHelper;
import co.com.udistrital.sin_nombre.vo.SesionVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla SESION.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class SesionDAO {

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public SesionDAO(Context context) {
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "[SesionDAO] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla SESION.
     * @return
     */
    public List<SesionVO> list() {
        try {
            List<SesionVO> listaSesion = new ArrayList<SesionVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SESION);
            Cursor listSesiones = db.rawQuery(sb.toString(), null);
            if(listSesiones.moveToFirst()){
                do{
                    SesionVO vo = new SesionVO();
                    vo.setIdSesion(listSesiones.getInt(0));
                    vo.setUsuario(listSesiones.getInt(1));
                    vo.setContrasena(listSesiones.getInt(2));
                    listaSesion.add(vo);
                } while(listSesiones.moveToNext());
            }
            return listaSesion;
        } catch (Exception e){
            Toast.makeText(contexto, "[list] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un registro en la tabla
     * SESION de la BDD recibiendo como parametro el id del registro.
     * @param idSesion
     * @return
     */
    public SesionVO consult(int idSesion) {
        SesionVO objSesion = new SesionVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SESION);
            sb.append(" WHERE ").append(dbh.SESION_ID).append(" = ").append(idSesion);
            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objSesion.setIdSesion(fila.getInt(0));
                objSesion.setUsuario(fila.getInt(1));
                objSesion.setContrasena(fila.getInt(2));
            }
            return objSesion;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de SESION
     * @param vo
     * @return
     */
    public boolean insert(SesionVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_SESION).append("(");
            sb.append(dbh.SESION_USER).append(dbh.SESION_PASS).append(")");
            sb.append(" VALUES (");
            sb.append("'" + vo.getUsuario() + "','").append(vo.getContrasena()+"'");
            sb.append(")");
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un registro de la tabla SESION recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(SesionVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_SESION).append(" SET ");
            sb.append(dbh.SESION_USER).append("='").append(vo.getUsuario()).append("',");
            sb.append(dbh.SESION_PASS).append("='").append(vo.getContrasena()).append("' ");
            sb.append("WHERE ").append(dbh.SESION_ID).append("=").append(vo.getIdSesion());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en SesionDAO " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un registro en la tabla SESION
     * @param idSesion
     * @return
     */
    public boolean delete(int idSesion) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_SESION);
            sb.append(" WHERE ").append(dbh.SESION_ID).append(" = ").append(idSesion);
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
