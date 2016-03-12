package co.com.udistrital.sin_nombre.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;

/**
 * Created by Fernando on 06/03/2016.
 */
public class InicioSesion extends AppCompatActivity {

    /**
     * Variables para el manejo de los componentes de aplicacion.
     */
    private EditText txtUsuario, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("[Sin_nombre]", "Iniciando vista principal de la aplicacion.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportActionBar().setTitle("Inicio Sesion");
        //getSupportActionBar().setIcon(R.drawable.icono_home);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("[Inicio Sesion]", "Generando Opciones de Menu");
        getMenuInflater().inflate(R.menu.menu_inicio_sesion, menu);
        return true;
    }

    /**
     * metodo para generar nueva actividad
     *
     * @param v
     */
    public void registrar(View v) {
        Intent intento = new Intent(getApplicationContext(), registro.class);
        startActivity(intento);
    }

    /**
     * metodo para generar un dialogo llamando un archivo  xml, el  cual permite la autentificacion del usuario y posteriormente
     * realiza el cambio de contraseña
     *
     * @param v
     */
    public void olvido(View v) {
        UsuarioDAO userDao = new UsuarioDAO(this);
        userDao.consult(1);
        final Dialog personal = new Dialog(this);
        personal.setContentView(R.layout.recuperar_cuenta);
        personal.setTitle("\tRecuperar Cuenta");
        final EditText txtUsuario = (EditText) personal.findViewById(R.id.txtnusuario);
        final EditText txtrespuesta = (EditText) personal.findViewById(R.id.txtrespuesta);
        final TextView txtpregunta = (TextView) personal.findViewById(R.id.txtpregunta);
        Button btnbuscar = (Button) personal.findViewById(R.id.btnbuscar);
        final Button btnenviar = (Button) personal.findViewById(R.id.btnenviar);
        btnenviar.setEnabled(false);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = "";//Buscar(txtUsuario.getText().toString());
                if (r == " -1 ") {
                    txtUsuario.setText("");
                    txtUsuario.setHint("No se encontro ese usuario");

                } else {
                    if (r == " -2 ")
                        txtUsuario.setHint("Debe digitar un usuario");
                    else {
                        txtpregunta.setText(r);
                        btnenviar.setEnabled(true);
                        txtUsuario.setEnabled(false);
                    }
                }
            }
        });

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtrespuesta.getText().toString().equals("")) {
                    txtrespuesta.setHint("Debe digitar una respuesta");
                } else {
                    if (txtrespuesta.getText().toString().equals("")) {//Respuesta(txtUsuario.getText().toString()))){
                        Toast.makeText(getApplicationContext(), "Cambiar Contraseña", Toast.LENGTH_SHORT).show();
                    } else {
                        txtrespuesta.setText("");
                        txtrespuesta.setHint("Respuesta Incorrecta");
                    }

                }
            }
        });

        personal.show();

    }

    /**
     * metodo encargador de revisar si el usuario ingresado en el dialogo fue encontrado y si lo esta
     *busca las preguntas que permiten realizar el cambio de contraseña
     * @param txt recibe el nombre del usuario
     * @return retorna un ' -1 ' si no se encontro el usuario digitado o un ' -2 ' si  no se ha
     * digitado  un usuario  o en caso contrario retorna un valor string con las preguntas del usuario
     */

   /* public String Buscar(String txt){
        if(!txt.equals("")){
            if (this.verificarUsuario(txt,2)==1){
                //DBManager manager2 = new DBManager(this);
                //String r=manager2.consultapregunta(txt);
                return "";//r;
            }else
                return " -1 ";
        }
        else
            return " -2 ";
    }*/

    /**
     * metodo encargado de buscar el usuario ingresado en el dialogo y si no lo esta devuelbe mensaje
     * @param usu es un  string el cual lleva el  nombre del usuario
     * @param opc es un int el  cual puede ser un 1 o un 2  el cual permite decidir que respuesta dar
     *            en caso de que no se encuentre el usuario
     * @return
     */
    /*public int verificarUsuario(String usu, int opc){
        DBManager manager2 = new DBManager(this);
        int r=manager2.consultanombreu(usu);
        if(r==0 && opc==1 ){
            txtUsuario.setText("");
            txtUsuario.setHint("No hay un usuario con este nombre");
            txtUsuario.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
        if(r==0 && opc==2 )
            return 0;
        return 1;
    }*/

    /**
     * metodo encargado de retornar las preguntas correspondiente al usuario digitado en el dialogo
     * @param txt variable string que lleva el  nombre de usuario para buscar su correspondientes
     *            preguntas
     * @return
     */
    /*public String Respuesta(String txt){
        DBManager manager2 = new DBManager(this);
        String r=manager2.consultarespuesta(txt);
        return r;
    }*/

    /**
     * metodo encargado de iniciar la actividad correspondiente al iniciar sesion o en caso contrario
     * devuelve mensaje de error en los datos ingresados
     * @param v
     */
    public void entrar (View v){
        //manager = new DBManager(this);
        int e=espaciosblancos();
        int u=0;
       /* if( e==1) {
            u = verificarUsuario(txtUsuario.getText().toString(),1);
        }
        if(e==1 && u==1){
            if(manager.consultacontrasenia(txtUsuario.getText().toString()).equals(txtPassword.getText().toString())){
                Intent intento = new Intent(this,Inicio.class);
                intento.putExtra("1 2 3", txtUsuario.getText().toString());
                startActivity(intento);
                finish();
            }else{
                Dialogo("Mensaje","La Contraseña ingresada es incorrecta",3);
                txtPassword.setText("");
                txtPassword.setHint(" La Contraseña ingresada es incorrecta");
                txtPassword.setHintTextColor(Color.parseColor("#51FF1218"));
            }

        }*/
    }

    /**
     * metodo encargado de verificar que los elementos editables del xml no se encuentren en blanco
     *
     * @return r=1 si no ahi espacion en blanco y  r=0 si hay espacios en blanco
     */
    public int espaciosblancos() {
        int r = 1;
        if ("".equals(txtUsuario.getText().toString())) {
            r = 0;
            txtUsuario.setHint("Debe ingresar su nombre de usuario");
            txtUsuario.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassword.getText().toString())) {
            r = 0;
            txtPassword.setHint("Debe ingresar su contraseña");
            txtPassword.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    /**
     * metodo que genera un dialogo el cual puede mostrar cualquier tipo de mensaje
     *
     * @param tit variable string que lleva en nombre del titulo del  dialogo
     * @param men variable string que lleva el mensaje que se quiere mostrar
     * @param opc variable entera que lleva un numero el  cual permite realizar una operacion
     *            especifica si es necesaria
     */
    public void Dialogo(String tit, final String men, final int opc) {
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error iniciosesion - Dialogo:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
