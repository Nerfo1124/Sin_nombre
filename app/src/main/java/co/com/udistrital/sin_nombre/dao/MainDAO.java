package co.com.udistrital.sin_nombre.dao;

/**
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public interface MainDAO {

    /**
     * <b>Descripcion: </b> Metodo encargado de enlistar una tabla completa consultada en la BDD.
     */
    public void list();

    /**
     * <b>Descripcion: </b> Metodo encargado de consultar un solo dato de la tabla solicitada en la
     * BDD.
     */
    public void consult();

    /**
     * <b>Descripcion: </b>Metodo encargado de insertar un o mas valores en la tabla solicitada en
     * la BDD.
     * @return
     */
    public boolean insert();

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizat uno o mas datos en la tabla solicitada en
     * la BDD.
     * @return
     */
    public boolean update();

    /**
     * <b>Descripcion: </b>Metodo encargado de borrar un valor de la tabla solicitada en la BDD.
     * @return
     */
    public boolean delete();
}
