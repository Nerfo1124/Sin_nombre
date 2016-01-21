package co.com.udistrital.sin_nombre.dao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rolando Baron on 19/09/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "presbicia.sqlite";
    private static final int DB_SCHEMA_VERSION = 1;
    Context contexto;

    public DataBaseHelper(Context context){
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FORMULA);
        db.execSQL(CREATE_TABLE_SISTEMA);
        db.execSQL(CREATE_TABLE_SESION);
        db.execSQL(CREATE_TABLE_RESTABLECER);
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Pendiente por validar con Rolando.
    }

    // Metodos para generar la BDD

    // Tabla de Usuarios
    public static final String TABLE_NAME_USUARIO = "USUARIO";
    public static final String USUARIO_ID = "usu_id";
    public static final String USUARIO_NOMBRE = "usu_nombre";
    public static final String USUARIO_APELLIDO1 = "usu_apellido1";
    public static final String USUARIO_APELLIDO2 = "usu_apellido2";
    public static final String USUARIO_NACIMIENTO = "usu_fecha_nacimiento";
    public static final String USUARIO_SEXO = "usu_sexo";
    public static final String USUARIO_SESION = "Sesion_ses_id";
    public static final String USUARIO_FORMULA = "Formula_for_id";
    public static final String USUARIO_SISTEMA = "Sistema_sis_id";
    public static final String USUARIO_REESTABLECER = "Restablecer_res_id";

    public static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE " + TABLE_NAME_USUARIO + " ( "
            + USUARIO_ID + " integer primary key autoincrement not null, "
            + USUARIO_NOMBRE + " text not null, "
            + USUARIO_APELLIDO1 + " text not null, "
            + USUARIO_APELLIDO2 + " text, "
            + USUARIO_NACIMIENTO + " text not null, "
            + USUARIO_SEXO + " text not null, "
            + USUARIO_SESION + " integer, "
            + USUARIO_FORMULA + " integer, "
            + USUARIO_SISTEMA + " integer, "
            + USUARIO_REESTABLECER + " integer, "
            + " ) ";

    // ========================================================

    //Tabla de Formula
    public static final String TABLE_NAME_FORMULA = "FORMULA";
    public static final String FORMULA_ID = "for_id";
    public static final String FORMULA_OJO_DER = "for_av_od";
    public static final String FORMULA_OJO_IZQ = "for_av_oi";

    public static final String CREATE_TABLE_FORMULA =
            "CREATE TABLE " + TABLE_NAME_FORMULA + " ( "
            + FORMULA_ID + " integer primary key autoincrement not null, "
            + FORMULA_OJO_DER + " integer not null, "
            + FORMULA_OJO_IZQ + " integer not null "
            + " ) ";

    // ========================================================

    // Tabla de Sesion
    public static final String TABLE_NAME_SESION = "SESION";
    public static final String SESION_ID = "ses_id";
    public static final String SESION_USER = "ses_user";
    public static final String SESION_PASS = "ses_pass";

    public static final String CREATE_TABLE_SESION =
            "CREATE TABLE " + TABLE_NAME_SESION + " ( "
            + SESION_ID + " integer primary key autoincrement not null, "
            + SESION_USER + " text not null, "
            + SESION_PASS + " text not null "
            + " ) ";

    // ========================================================

    // Tabla de Sistema
    public static final String TABLE_NAME_SISTEMA = "SISTEMA";
    public static final String SISTEMA_ID = "sis_id";
    public static final String SISTEMA_TAM_FUENTE = "sis_tam_fuente";
    public static final String SISTEMA_FRECUENCIA = "sis_frecuencia";

    public static final String CREATE_TABLE_SISTEMA =
            "CREATE TABLE " + TABLE_NAME_SISTEMA + " ( "
            + SISTEMA_ID + " integer primary key autoincrement not null"
            + SISTEMA_TAM_FUENTE + " integer not null, "
            + SISTEMA_FRECUENCIA + " integer not null "
            + " ) ";

    // ========================================================

    // Tabla de Restablecer
    public static final String TABLE_NAME_RESTABLECER = "RESTABLECER";
    public static final String RESTABLECER_ID = "res_id";
    public static final String RESTABLECER_QUEST1 = "res_pregunta1";
    public static final String RESTABLECER_ANSW1 = "res_respuesta1";
    public static final String RESTABLECER_QUEST2 = "res_pregunta2";
    public static final String RESTABLECER_ANSW2 = "res_respuesta2";
    public static final String RESTABLECER_TAMANO_FUENTE = "res_tam_fuente_sistema";

    public static final String CREATE_TABLE_RESTABLECER =
            "CREATE TABLE " + TABLE_NAME_RESTABLECER + " ( "
            + RESTABLECER_ID + " integer primary key autoincrement not null, "
            + RESTABLECER_QUEST1 + " text not null, "
            + RESTABLECER_ANSW1 + " text not null, "
            + RESTABLECER_QUEST2 + " text, "
            + RESTABLECER_ANSW2 + " text"
            + " ) ";

    // ========================================================
}
