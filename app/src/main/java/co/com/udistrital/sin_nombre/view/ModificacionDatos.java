package co.com.udistrital.sin_nombre.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.vo.SesionVO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

public class ModificacionDatos extends AppCompatActivity {

    private static String TAG_LOG = "[Sin_nombre]";
    
    /**
     * vector que almacena cada una de las preguntas separadamente
     */
    String[] opciones = {"Seleccione una pregunta",
            "¿Nombre de tu mascota preferida?",
            "¿Lugar de nacimiento de tu padre?",
            "¿Cancion favorita?",
            "¿Mejor amigo?"};

    private Spinner preguntasUno;
    private Spinner preguntasDos;

    private EditText txtIdUsuario, txtNombreCompleto, txtApellidoCompleto, txtFechaNacimiento, txtSexoM;
    private EditText txtUser, txtPassUno, txtPassDos;
    private UsuarioVO usuario;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_datos);

        /* Codigo para llenar los Spinner */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        preguntasUno = (Spinner) findViewById(R.id.spPreguntaUno);
        preguntasDos = (Spinner) findViewById(R.id.spPreguntaDos);

        preguntasUno.setAdapter(adapter);
        preguntasDos.setAdapter(adapter);

        txtIdUsuario = (EditText) findViewById(R.id.txtIdUserMD);
        txtNombreCompleto = (EditText) findViewById(R.id.txtNombreMD);
        txtApellidoCompleto = (EditText) findViewById(R.id.txtApellidoMD);
        txtFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimientoMD);
        txtSexoM = (EditText) findViewById(R.id.txtSexoMD);

        txtUser = (EditText) findViewById(R.id.txtUserMD);
        txtPassUno = (EditText) findViewById(R.id.txtPassUnoMD);
        txtPassDos = (EditText) findViewById(R.id.txtPassDosMD);

        dao = new UsuarioDAO(this);

        try {
            usuario = dao.consult(1);

            Log.d(TAG_LOG, "Id: " + usuario.getIdUsuario());
            Log.d(TAG_LOG, "Nombre: " + usuario.getNombreUsuario());
            Log.d(TAG_LOG, "Apellido1: " + usuario.getApellido1Usuario());
            Log.d(TAG_LOG, "Apellido2: " + usuario.getApellido2Usuario());
            Log.d(TAG_LOG, "Fecha Nacimiento: " + usuario.getFechaNacimiento());
            Log.d(TAG_LOG, "Sexo: " + usuario.getSexo());

            txtIdUsuario.setText("" + usuario.getIdUsuario());
            txtNombreCompleto.setText("" + usuario.getNombreUsuario());
            txtApellidoCompleto.setText("" + usuario.getApellido1Usuario() + " " + usuario.getApellido2Usuario());
            txtFechaNacimiento.setText("" + usuario.getFechaNacimiento());
            txtSexoM.setText("" + usuario.getSexo());

            SesionVO sesion = usuario.getSesionUsuario();
            txtUser.setText("" + sesion.getUsuario());
            txtPassUno.setText("" + sesion.getContrasena());
            txtPassDos.setText("" + sesion.getContrasena());
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error en la ejecucion de la Actividad: ", e);
        }
    }

    public void modificarDatos(View v) {
        dao = new UsuarioDAO(this);
        Log.d(TAG_LOG, "Id: " + txtIdUsuario.getText().toString());
        Log.d(TAG_LOG, "Id: " + txtNombreCompleto.getText().toString());
        Log.d(TAG_LOG, "Id: " + txtApellidoCompleto.getText().toString());
        Log.d(TAG_LOG, "Id: " + txtFechaNacimiento.getText().toString());
        Log.d(TAG_LOG, "Id: " + txtSexoM.getText().toString());

        usuario.setIdUsuario(Integer.parseInt(txtIdUsuario.getText().toString()));
        usuario.setNombreUsuario(txtNombreCompleto.getText().toString());
        String[] apellidos = txtApellidoCompleto.getText().toString().split(" ");
        if (apellidos[1] != null || !apellidos[1].trim().equals("")) {
            usuario.setApellido1Usuario(apellidos[0]);
            usuario.setApellido2Usuario(apellidos[1]);
        } else {
            usuario.setApellido1Usuario(apellidos[0]);
            usuario.setApellido2Usuario("");
        }
        usuario.setFechaNacimiento(txtFechaNacimiento.getText().toString());
        usuario.setSexo(txtSexoM.getText().toString());
        dao.update(usuario);
    }
}
