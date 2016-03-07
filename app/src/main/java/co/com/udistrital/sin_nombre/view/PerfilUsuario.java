package co.com.udistrital.sin_nombre.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

public class PerfilUsuario extends AppCompatActivity {

    private EditText txtNameUsuario, txtFechaUsuario, txtSexo, txtIdSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("[Sin_nombre]", "Ingresando a la vista principal de Perfil.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras

        UsuarioDAO userDao = new UsuarioDAO(this);

        txtNameUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtFechaUsuario = (EditText) findViewById(R.id.txtFechaNacimiento);
        txtSexo = (EditText) findViewById(R.id.txtSexoUsuario);
        txtIdSesion = (EditText) findViewById(R.id.txtIdSesion);

        // TODO Mostrando Valores en la Vista de Perfil
        try {
            UsuarioVO user;
            user = userDao.consult(1);

            txtNameUsuario.setText(user.getNombreUsuario() + " " + user.getApellido1Usuario() + " " + user.getApellido2Usuario());
            txtFechaUsuario.setText(user.getFechaNacimiento());
            txtSexo.setText(user.getSexo());
            Log.d("[Sin_nombre]", "[onCreate] Codigo usuario: " + user.getSesionUsuario().getIdSesion());
            txtIdSesion.setText("" + user.getSesionUsuario().getIdSesion());
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "[onCreate] Error en la consulta del usuario: ", ex);
        }
    }
}