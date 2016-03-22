package co.com.udistrital.sin_nombre.view;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.RestablecerDAO;
import co.com.udistrital.sin_nombre.dao.SesionDAO;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.security.Encrypter;
import co.com.udistrital.sin_nombre.util.DateDialog;
import co.com.udistrital.sin_nombre.vo.ReestablecerVO;
import co.com.udistrital.sin_nombre.vo.SesionVO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

public class ModificacionDatos extends AppCompatActivity {

    private static String TAG_LOG = "[Sin_nombre]";

    /**
     * Elemento que permite generar las pestañas
     */
    private TabHost TabHDos;
    /**
     * vector que almacena cada una de las preguntas separadamente
     */
    String[] opciones = {"Seleccione una pregunta",
            "¿Nombre de tu mascota preferida?",
            "¿Lugar de nacimiento de tu padre?",
            "¿Cancion favorita?",
            "¿Mejor amigo?"};

    /**
     * Variable de usuario en sesion.
     */
    private int idUsuario;

    private Spinner preguntasUno;
    private Spinner preguntasDos;

    private EditText txtIdUsuario, txtNombreCompleto, txtApellidoCompleto, txtFechaNacimiento, txtSexoM;
    private EditText txtUser, txtPassUno, txtPassDos;
    private EditText txtRespuestaUno, txtRespuestaDos;
    private UsuarioVO usuario;
    private UsuarioDAO dao;
    private SesionVO sesion;
    private SesionDAO daoS;
    private ReestablecerVO restablecer;
    private RestablecerDAO daoR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_datos);
        cargarDatosIniciales();
        cargarTabHost();
        vistaInicial();
    }

    public void cargarDatosIniciales() {
        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        idUsuario = Integer.parseInt(bundle.getString("idUsuarioM"));
        Log.d(TAG_LOG, "Parametro recibido: " + idUsuario);
    }

    public void vistaInicial() {
        /* Codigo para llenar los Spinner */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        preguntasUno = (Spinner) findViewById(R.id.spPreguntaUno);
        preguntasDos = (Spinner) findViewById(R.id.spPreguntaDos);

        preguntasUno.setAdapter(adapter);
        preguntasDos.setAdapter(adapter);

        txtRespuestaUno = (EditText) findViewById(R.id.txtRespuestaUnoMD);
        txtRespuestaDos = (EditText) findViewById(R.id.txtRespuestaDosMD);

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
            usuario = dao.consult(idUsuario);
            txtIdUsuario.setText("" + usuario.getIdUsuario());
            txtNombreCompleto.setText("" + usuario.getNombreUsuario());
            txtApellidoCompleto.setText("" + usuario.getApellido1Usuario() + " " + usuario.getApellido2Usuario());
            txtFechaNacimiento.setText("" + usuario.getFechaNacimiento());
            txtSexoM.setText("" + usuario.getSexo());

            sesion = usuario.getSesionUsuario();
            txtUser.setText("" + sesion.getUsuario());
            txtPassUno.setText("" + sesion.getContrasena());
            txtPassDos.setText("" + sesion.getContrasena());
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error en la ejecucion de la Actividad: ", e);
        }
    }

    private void cargarTabHost() {
        TabHDos = (TabHost) findViewById(R.id.tabHost2);
        TabHDos.setup();
        TabHDos.addTab(TabHDos.newTabSpec("ModUno").setIndicator("Datos Personales").setContent(R.id.modificarUno));
        TabHDos.addTab(TabHDos.newTabSpec("ModDos").setIndicator("Datos de Seguridad").setContent(R.id.modificarDos));
        TabHDos.addTab(TabHDos.newTabSpec("ModTres").setIndicator("Datos de Sesion").setContent(R.id.modificarTres));
    }

    public void modificarDatosPersonales(View v) {
        dao = new UsuarioDAO(this);
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
        Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
        this.finish();
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de realizar la modificacion de los datos para restablecer
     * la contraseña de seguridad e inicio de sesion.
     *
     * @param v
     */
    public void modificarSeguridad(View v) {
        daoR = new RestablecerDAO(this);
        restablecer = dao.consult(idUsuario).getRestablecerUsuario();
        String quest1 = preguntasUno.getSelectedItem().toString();
        String quest2 = preguntasDos.getSelectedItem().toString();
        if (!quest1.equals(quest2)) {
            if (!txtRespuestaUno.getText().toString().trim().equals("") && !txtRespuestaDos.getText().toString().equals("")) {
                restablecer.setPregunta1(quest1);
                restablecer.setPregunta2(quest2);
                restablecer.setRespuesta1(txtRespuestaUno.getText().toString());
                restablecer.setRespuesta2(txtRespuestaDos.getText().toString());
                daoR.update(restablecer);
                Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                txtRespuestaUno.setText("");
                txtRespuestaUno.setHint("Debe ingresar respuesta a la pregunta.");
                txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
                txtRespuestaDos.setText("");
                txtRespuestaDos.setHint("Debe ingresar respuesta a la pregunta.");
                txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
            }
        } else {
            txtRespuestaUno.setText("");
            txtRespuestaUno.setHint("Las preguntas deben ser diferentes.");
            txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
            txtRespuestaDos.setText("");
            txtRespuestaDos.setHint("Las preguntas deben ser diferentes.");
            txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de actualizar el nombre de usuario y la nueva
     * contraseña del usuario en sesion
     *
     * @param v
     */
    public void modificarDatosSesion(View v) {
        daoS = new SesionDAO(this);
        sesion = dao.consult(idUsuario).getSesionUsuario();
        sesion.setUsuario(txtUser.getText().toString());
        if (validarCamposPass()) {
            String claveAux = txtPassDos.getText().toString();
            String claveUpdate = Encrypter.getStringMessageDigest(claveAux, Encrypter.SHA256);
            sesion.setContrasena(claveUpdate);
            daoS.update(sesion);
            Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            txtPassUno.setText("");
            txtPassUno.setHint("Las contraseñas deben ser iguales");
            txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
            txtPassDos.setText("");
            txtPassDos.setHint("Las contraseñas deben ser iguales");
            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
        }
    }

    public boolean validarCamposPass() {
        boolean response = false;
        String valor1 = txtPassUno.getText().toString();
        String valor2 = txtPassDos.getText().toString();
        if (valor1.trim().equals(valor2)) {
            response = true;
        }
        return response;
    }

    public void limpiarCamposSesion(View v) {
        txtUser.setText("");
        txtPassUno.setText("");
        txtPassDos.setText("");
    }

    /**
     * <b>Descripcion: </b> Metodo que permite cargar un Dialogo para seleccionar una fecha.
     */
    public void abrir() {
        txtFechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog Dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Dialog.show(ft, "DatePiker");
                }
            }
        });
    }
}
