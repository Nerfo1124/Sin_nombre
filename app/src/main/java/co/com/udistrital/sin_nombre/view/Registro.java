package co.com.udistrital.sin_nombre.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.SesionDAO;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.util.DateDialog;
import co.com.udistrital.sin_nombre.util.pantalla_on_off;
import co.com.udistrital.sin_nombre.vo.FormulaVO;
import co.com.udistrital.sin_nombre.vo.ReestablecerVO;
import co.com.udistrital.sin_nombre.vo.SesionVO;
import co.com.udistrital.sin_nombre.vo.SistemaVO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

/**
 * Created by Fernando on 06/03/2016.
 */
public class Registro extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    /**
     * vector que almacena cada una de las preguntas separadamente
     */
    String[] opciones = {"Seleccione una pregunta",
            "¿Nombre de tu mascota preferida?",
            "¿Lugar de nacimiento de tu padre?",
            "¿Cancion favorita?",
            "¿Mejor amigo?"};

    private UsuarioVO usuarioReg = new UsuarioVO();
    private UsuarioDAO objUsuarioDao;
    private SesionDAO objSesionDao;
    private String tamanioSistema;
    /**
     * Elementos para los grupos de tipo de sexo/formula
     */
    private RadioGroup grupoSexo, grupoFamilia;
    /**
     * Permiten agrupar  separadamente los elementos segun sea por formula o por ajuste manual
     */
    private RelativeLayout layoutAnimadoUno, layoutAnimadoDos;
    /**
     * Elemento que permite almacenar en forma de lista preguntas
     */
    private Spinner selector;
    /**
     * Elemento que permite generar las pestañas
     */
    private TabHost TbH;
    /**
     * Elementos que hacen referencia a los datos necesarios del usuario
     */
    private EditText txtFecha, txtNombreu, txtPassUno, txtPassDos, txtNombre, txtApellido, txtRespuesta;
    private SeekBar barra, barra2;//elementos que permite seleccionar la formula(numero) de cada ojo
    private TextView iz, de;//elementos que llevaran el valor de formula del ojo izquierdo y  del ojo derecho
    private EditText texto;
    private EditText tamanioF;
    private SeekBar seekBar;
    private NumberPicker fre;//elemento que permite selecionar el numero de horas para la frecuencia de tiempo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("[Sin_nombre]", "Ingresando a la vista principal de Registro.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras
        objUsuarioDao = new UsuarioDAO(this);
        objSesionDao = new SesionDAO(this);
        pestanias();
        referenciasuno();
        referenciasdos();
        referenciastres();
        abrir();
    }

    /**
     * metodo  que permite retroceder sin perder la informacion ingresada en la actividad anterior
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * este metodo se encarga de referenciar los elementos creados globalmente con los elementos que hay en el XML en la primera pestaña
     * esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciasuno() {
        layoutAnimadoUno = (RelativeLayout) findViewById(R.id.dinamico1);
        layoutAnimadoDos = (RelativeLayout) findViewById(R.id.dinamico2);
        grupoSexo = (RadioGroup) findViewById(R.id.radiogrupoR1);
        grupoFamilia = (RadioGroup) findViewById(R.id.radiogrupo2R1);
        txtFecha = (EditText) findViewById(R.id.txtFechaR1);
        txtNombreu = (EditText) findViewById(R.id.txtUsuarioR1);
        txtPassUno = (EditText) findViewById(R.id.txtPasswordUnoR1);
        txtPassDos = (EditText) findViewById(R.id.txtPasswordDosR1);
        txtNombre = (EditText) findViewById(R.id.txtNombreR1);
        txtApellido = (EditText) findViewById(R.id.txtApellidoR1);
        selector = (Spinner) findViewById(R.id.selectorR1);
        txtRespuesta = (EditText) findViewById(R.id.txtRespuestaR1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        //se agrega el  vector string a un vector adpatador  con el fin de que este se pueda ajustar al sppiner.
        selector.setAdapter(adapter);

    }

    /**
     * este metodo se encarga de referenciar los elementos creados globalmente con los elementos que hay en el XML en la segunda pestaña
     * esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciasdos() {
        seekBar = (SeekBar) findViewById(R.id.sbBarra);
        texto = (EditText) findViewById(R.id.txtTexto);
        seekBar.setProgress((int) texto.getTextSize());
        tamanioF = (EditText) findViewById(R.id.txtTamanioFuente);
        tamanioF.setText("Tamaño de la Fuente: " + seekBar.getProgress() + "%");
        barra = (SeekBar) findViewById(R.id.barra);
        barra.setOnSeekBarChangeListener(this);
        barra2 = (SeekBar) findViewById(R.id.barra2);
        barra2.setOnSeekBarChangeListener(this);
        iz = (TextView) findViewById(R.id.lblizq);
        de = (TextView) findViewById(R.id.lblder);

        tamanioSistema = "" + texto.getTextSize();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tamanioF.setText("Tamaño de la Fuente: " + progress + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void referenciastres() {
        fre = (NumberPicker) findViewById(R.id.numero);
        fre.setMaxValue(24);
        fre.setMinValue(1);
        fre.setWrapSelectorWheel(false);
    }

    /**
     * metodo que ajusta el progreso de las barras.
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.equals(barra))
            iz.setText("" + progress);
        if (seekBar.equals(barra2))
            de.setText("" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void mas(View v) {
        barra.setProgress(barra.getProgress() + 1);
    }

    public void menos(View v) {
        barra.setProgress(barra.getProgress() - 1);
    }

    public void mas2(View v) {
        barra2.setProgress(barra2.getProgress() + 1);
    }

    public void menos2(View v) {
        barra2.setProgress(barra2.getProgress() - 1);
    }

    /**
     * metodo  que permite automaticamente abrir un dialogo que permite selecionar fechas.
     */
    public void abrir() {
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    /**
     * este metodo realiza cambios en el tabhost, tales como agregar pestañas con sus respectivos diseños(Layout)
     */
    public void pestanias() {
        TbH = (TabHost) findViewById(R.id.tabHost);
        TbH.setup();
        TbH.addTab(TbH.newTabSpec("uno").setIndicator("Registro 1").setContent(R.id.registro1));
        TbH.addTab(TbH.newTabSpec("dos").setIndicator("Registro 2").setContent(R.id.registro2));
        TbH.addTab(TbH.newTabSpec("tres").setIndicator("Registro 3").setContent(R.id.registro3));
        TbH.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == "uno")
                    mostrar(tabId);
            }
        });
    }

    /**
     * este metodo hace referencia a un jbutton el cual debera revisar que todos los datos ingresados en la primera pestaña
     * se han correctos, al igual  que ocultar o mostrar el  layout  correspondiente a la siguiente pestaña(Formula, configuracion manual)
     * Tener en cuanta generalmente si se devuelve 0 cero  es por que los datos son incorrectos
     * pero   si  se devuelve uno 1 es por que los valores son los adecuados
     *
     * @param v
     */

    public void continuar(View v) {
        int p = espaciosblancos();
        int r = 0, r2 = 0, r3 = 0;
        if (p == 1) {
            r = compararFecha();
            if (r == 1) {
                r2 = compararPasswords();
            }
            if (r2 == 1) {
                // Codigo o funcion que determina si el usuario ingresado  ya esta creado
                String nombreUsuario = txtNombreu.getText().toString();
                Log.d("[Sin_nombre]", "[continuar] Nombre de usuario a consultar: " + nombreUsuario);
                r3 = objSesionDao.consultaNombreU(nombreUsuario);
                if (r3 == 1) {
                    txtNombreu.setText("");
                    Log.d("[Sin_nombre]", "[continuar] Ya hay un usuario con el nombre ingresado.");
                    txtNombreu.setHint("Ya hay un usuario con este nombre");
                    txtNombreu.setHintTextColor(Color.parseColor("#51FF1218"));
                }
                r3 = 1;
            }
            if (layoutAnimadoUno.getVisibility() == View.GONE)
                layoutAnimadoUno.setVisibility(View.VISIBLE);//linea que muestra el layuot junto a todos sus elementos
            if (layoutAnimadoDos.getVisibility() == View.VISIBLE)
                layoutAnimadoDos.setVisibility(View.GONE);//metodo que oculpa el layout junto con todos sus elementos
        }
        Toast.makeText(this, "" + r + " " + r2 + " " + r3 + " " + p, Toast.LENGTH_LONG).show();
        if (r == 1 && r2 == 1 && r3 == 1 && p == 1) {
            if (grupoFamilia.getCheckedRadioButtonId() != R.id.radiosiR1) {
                if (layoutAnimadoDos.getVisibility() == View.GONE)
                    layoutAnimadoDos.setVisibility(View.VISIBLE);
                if (layoutAnimadoUno.getVisibility() == View.VISIBLE)
                    layoutAnimadoUno.setVisibility(View.GONE);
            }
            TbH.setCurrentTab(1);
        }
    }

    public void siguiente(View v) {
        if ("" + texto.getTextSize() != null && "" + texto.getTextSize() != "") {
            TbH.setCurrentTab(2);
        }
    }

    /**
     * metodo  que verifica que los edittext tengan espacion en blanco o con " " y  en caso tal de
     * cumplan esta condicion se devolvera un entero con valor cero 0 o  si todos los edittext tienen
     * datos validos entonces se retornara un  uno 1. tambien muestra mensaje de datos no validos a cada
     * edittext que tiene datos invalidos
     *
     * @return valor 0 0 1.
     */
    public int espaciosblancos() {
        int r = 1;
        if ("".equals(txtNombreu.getText().toString()) || 0 == espacios(txtNombreu.getText().toString())) {
            r = 0;
            txtNombreu.setHint("Debe ingresar un nombre de usuario");
            txtNombreu.setHintTextColor(Color.parseColor("#51FF1218"));
        }

        if ("".equals(txtNombre.getText().toString()) || 0 == espacios(txtNombre.getText().toString())) {
            r = 0;
            txtNombre.setText("");
            txtNombre.setHint("Debe ngresar su nombre");
            txtNombre.setHintTextColor(Color.parseColor("#51FF1218"));//cambia a color rojo
        }

        if ("".equals(txtApellido.getText().toString()) || 0 == espacios(txtApellido.getText().toString())) {
            r = 0;
            txtApellido.setText("");
            txtApellido.setHint("Debe ingresar su apellido");
            txtApellido.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtFecha.getText().toString())) {
            r = 0;
            txtFecha.setText("");
            txtFecha.setHint("Debe ingresar su fecha de nacimiento");
            txtFecha.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassUno.getText().toString())) {
            r = 0;
            txtPassUno.setText("");
            txtPassUno.setHint("Debe ingresar una contraseña");
            txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassDos.getText().toString())) {
            r = 0;
            txtPassDos.setText("");
            txtPassDos.setHint("Debe ingresar la contraseña");
            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtRespuesta.getText().toString()) || 0 == espacios(txtRespuesta.getText().toString())) {
            r = 0;
            txtRespuesta.setText("");
            txtRespuesta.setHint("Debe ingresar una respuesta acorde");
            txtRespuesta.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    /**
     * metodo que verifica que la fecha ingresada este en el rango permitido y en caso de que sea
     * correcta retorna un uno 1 y en caso contrario un cero 0. muestra mensaje en caso de que haya
     * error con la fecha
     *
     * @return valor 1 o 0
     */
    public int compararFecha() {
        Calendar f = Calendar.getInstance();
        int anio = f.get(Calendar.YEAR);
        String[] fecha = txtFecha.getText().toString().split("-");
        anio = anio - Integer.parseInt(fecha[2]);
        if (anio > 15)
            return 1;
        else {
            txtFecha.setText("");
            txtFecha.setHint("Su Fecha Debe Ser Menor Al Año 2000");
            txtFecha.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo que compara las dos contraseñas ingresas, en caso de que las contraseñas no considan
     * se devolvera un entero  con el valor  cero 0, o en caso contrario se devolvera un uno 1.
     *
     * @return
     */
    public int compararPasswords() {
        if (txtPassUno.getText().toString().equals(txtPassDos.getText().toString()))
            return 1;
        else {
            txtPassDos.setText("");
            txtPassDos.setHint("Las contraseñas no coinciden, repitala");
            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo que muestra mensaje
     *
     * @param m variable string que lleva el  mensaje que se va a mostrar
     */

    public void mostrar(String m) {
        Toast.makeText(this, " mensaje " + m, Toast.LENGTH_LONG).show();
    }

    /**
     * metodo  que recive un string, y revisa si este string comienza por algun espacio en blanco " "
     *
     * @param t variable que contiene el texto  a revisar
     * @return retorna 0 si el texto  inicia  con " " o  1 si inicia con algun otro  caracter diferente
     * al " "
     */
    public int espacios(String t) {
        if (t.charAt(0) == ' ')
            return 0;//retorna cero si el parametro  comienza con un espacio
        else
            return 1;//retorna uno  si el parametro  no comienza con un espacio
    }

    /**
     * <b>Descripcion: </b> Metodo que hace referencia a un jbutton encargado de aumentar el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     *
     * @param v
     */
    public void masET(View v) {
        int progreso;
        int newProgreso;
        if (v.getId() == R.id.btnMas) {
            progreso = seekBar.getProgress();
            if (progreso < 100) {
                newProgreso = progreso + 1;
                seekBar.setProgress(newProgreso);
                tamanioF.setText("Tamaño de la Fuente: " + newProgreso + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, newProgreso);
            }
        }
    }

    /**
     * <b>Descripcion: </b> Metodo que hace referencia a un jbutton encargado de disminuir el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     *
     * @param v
     */
    public void menosET(View v) {
        int progreso;
        int newProgreso;
        if (v.getId() == R.id.btnMenos) {
            progreso = seekBar.getProgress();
            if (progreso > 0) {
                newProgreso = progreso - 1;
                seekBar.setProgress(newProgreso);
                tamanioF.setText("Tamaño de la Fuente: " + newProgreso + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, newProgreso);
            }
        }
    }

    public void llenarUsuario() {
        usuarioReg.setNombreUsuario(txtNombre.getText().toString());
        Log.d("[Sin_nombre]", "Nombre: " + txtNombre.getText());
        String[] apellidos = txtApellido.getText().toString().split(" ");
        usuarioReg.setApellido1Usuario(apellidos[0]);
        Log.d("[Sin_nombre]", "[llenarUsuario] Tamaño del array: " + apellidos.length);
        if (apellidos.length > 1) {
            if (apellidos[1] != null && apellidos[1] != "") {
                usuarioReg.setApellido2Usuario(apellidos[1]);
            }
        }
        usuarioReg.setFechaNacimiento(txtFecha.getText().toString());
        if (grupoSexo.getCheckedRadioButtonId() == R.id.rbhombreR1) {
            usuarioReg.setSexo("Masculino");
        } else if (grupoSexo.getCheckedRadioButtonId() == R.id.rbmujerR1) {
            usuarioReg.setSexo("Femenino");
        }
        usuarioReg.setSesionUsuario(llenarSesion());
        usuarioReg.setFormulaUsuario(llenarFormula());
        usuarioReg.setConfigUsuario(llenarSistema());
        usuarioReg.setRestablecerUsuario(llenarCuenta());

        objUsuarioDao.insert(usuarioReg);
        Toast.makeText(this, ":D :D :D :(", Toast.LENGTH_LONG).show();
        Log.i("[Sin_nombre]", " [llenarUsuario] Usuario registrado satisfactoriamente.");
    }

    public SesionVO llenarSesion() {
        SesionVO datoSesion = new SesionVO();
        datoSesion.setUsuario(txtNombreu.getText().toString());
        datoSesion.setContrasena(txtPassDos.getText().toString());
        return datoSesion;
    }

    public FormulaVO llenarFormula() {
        FormulaVO datoFormula = new FormulaVO();
        datoFormula.setaVisualOD(Float.parseFloat(iz.getText().toString()));
        datoFormula.setaVisualOI(Float.parseFloat(de.getText().toString()));
        datoFormula.setTamanioFuente("" + texto.getTextSize());
        return datoFormula;
    }


    public SistemaVO llenarSistema() {
        SistemaVO datosistema = new SistemaVO();
        datosistema.setFrecuencia(fre.getValue());
        datosistema.setTamanoFuente(texto.getTextSize());
        return datosistema;
    }

    /**
     * Metodo encargado de llenar la tabla Reestablecer.
     *
     * @return
     */
    public ReestablecerVO llenarCuenta() {
        ReestablecerVO datorestablecer = new ReestablecerVO();
        datorestablecer.setPregunta1(selector.getSelectedItem().toString());
        datorestablecer.setRespuesta1(txtRespuesta.getText().toString());
        datorestablecer.setPregunta2(selector.getSelectedItem().toString());
        datorestablecer.setRespuesta2(txtRespuesta.getText().toString());
        datorestablecer.setTamanoFuente(tamanioSistema);
        return datorestablecer;
    }

    public void terminar(View v) {
        try {
            llenarUsuario();
            this.finish();
        } catch (Exception ex) {
            Log.d("[Sin_nombre]", "Error al mostrar Perfil de Usuario", ex);
        }
    }
}
