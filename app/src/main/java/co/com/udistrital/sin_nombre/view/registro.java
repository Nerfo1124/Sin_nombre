package co.com.udistrital.sin_nombre.view;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import co.com.udistrital.sin_nombre.util.DateDialog;

public class registro extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private RadioGroup gruposexo, grupoformula;//elementos para los grupos de tipo de sexo/formula
    private RelativeLayout layoutAnimadouno, layoutAnimadodos;// permiten agrupar  separadamente los elementos segun sea por formula o por ajuste manual
    private Spinner selector; //elemento que permite almacenar en forma de lista preguntas
    private TabHost TbH;//elemento que permite generar las pestañas
    private EditText txtfecha, txtnombreu, txtcontra, txtcontrados, txtnombre, txtapellido, txtrespuesta;//elementos que hacen referencia a los datos necesarios del usuario
    private SeekBar barra, barra2;//elementos que permite seleccionar la formula(numero) de cada ojo
    private TextView iz,de;//elementos que llevaran el valor de formula del ojo izquierdo y  del ojo derecho
    private EditText texto;
    private NumberPicker fre;//elemento que permite selecionar el numero de horas para la frecuencia de tiempo
    String[] opciones = {"Seleccione una pregunta", "¿Nombre de tu mascota preferida?", "¿Lugar de nacimiento de tu padre?", "¿Cancion favorita?", "¿Mejor amigo?"};
    //vector que almacena cada una de las preguntas separadamente
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras
        pestanias();
        referenciasuno();
        referenciasdos();
        referenciastres();
        abrir();
    }

    /**
     * metodo  que permite retroceder sin perder la informacion ingresada en la actividad anterior
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
     *  esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciasuno() {
        layoutAnimadodos = (RelativeLayout) findViewById(R.id.dinamico2);
        layoutAnimadouno = (RelativeLayout) findViewById(R.id.dinamico1);
        gruposexo = (RadioGroup) findViewById(R.id.radiogrupoR1);
        grupoformula = (RadioGroup) findViewById(R.id.radiogrupo2R1);
        txtfecha = (EditText) findViewById(R.id.txtfechaR1);
        txtnombreu = (EditText) findViewById(R.id.txtusuarioR1);
        txtcontra = (EditText) findViewById(R.id.txtcontraseñarR1);
        txtcontrados = (EditText) findViewById(R.id.txtcontraseña2R1);
        txtnombre = (EditText) findViewById(R.id.txtnombreR1);
        txtapellido = (EditText) findViewById(R.id.txtapellidoR1);
        selector = (Spinner) findViewById(R.id.selectorR1);
        txtrespuesta = (EditText) findViewById(R.id.txtpreguntaR1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        //se agrega el  vector string a un vector adpatador  con el fin de que este se pueda ajustar al sppiner.
        selector.setAdapter(adapter);

    }
    /**
     * este metodo se encarga de referenciar los elementos creados globalmente con los elementos que hay en el XML en la segunda pestaña
     *  esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciasdos(){
        barra = (SeekBar)findViewById(R.id.barra);
        barra.setOnSeekBarChangeListener(this);
        barra2 = (SeekBar)findViewById(R.id.barra2);
        barra2.setOnSeekBarChangeListener (this);
        iz = (TextView)findViewById(R.id.lblizq);
        de = (TextView)findViewById(R.id.lblder);
        texto = (EditText)findViewById(R.id.txttexto2);
    }

    public void referenciastres(){
        fre= (NumberPicker)findViewById(R.id.numero);
        fre.setMaxValue(24);
        fre.setMinValue(1);
        fre.setWrapSelectorWheel(false);
    }

    /**
     * metodo que ajusta el progreso de las barras.
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(barra))
            iz.setText(""+progress);
        if(seekBar.equals(barra2))
            de.setText(""+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void mas(View v){
        barra.setProgress(barra.getProgress() + 1);
    }
    public void menos(View v){
        barra.setProgress(barra.getProgress() - 1);
    }
    public  void mas2(View v){
        barra2.setProgress(barra2.getProgress() + 1);
    }
    public  void menos2(View v){
        barra2.setProgress(barra2.getProgress() - 1);
    }
    /**
     * metodo  que permite automaticamente abrir un dialogo que permite selecionar fechas.
     */
    public void abrir() {
        txtfecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
     * @param v
     */

    public void continuar(View v) {
        TbH.setCurrentTab(1);
        int p = espaciosblancos();
        int r = 0, r2 = 0, r3 = 0;
        if (p == 1) {
            r = compararfecha();
            if (r == 1) {
                r2 = compararcontraseñas();
            }
            if (r2 == 1) {
                // r3=verificarUsuario(); aqui ira el codigo o funcion que determina si el usuario ingresado  ya esta creado
                r3 = 1;
            }
            if (layoutAnimadouno.getVisibility() == View.GONE)
                layoutAnimadouno.setVisibility(View.VISIBLE);//linea que muestra el layuot junto a todos sus elementos
            if (layoutAnimadodos.getVisibility() == View.VISIBLE)
                layoutAnimadodos.setVisibility(View.GONE);//metodo que oculpa el layout junto con todos sus elementos
        }
        if (r == 1 && r2 == 1 && r3 == 1 && p == 1) {
            if (grupoformula.getCheckedRadioButtonId() == R.id.radiosiR1) {

            } else {
                if (layoutAnimadodos.getVisibility() == View.GONE)
                    layoutAnimadodos.setVisibility(View.VISIBLE);
                if (layoutAnimadouno.getVisibility() == View.VISIBLE)
                    layoutAnimadouno.setVisibility(View.GONE);

            }

        }
    }

    /**
     * metodo  que verifica que los edittext tengan espacion en blanco o con " " y  en caso tal de
     * cumplan esta condicion se devolvera un entero con valor cero 0 o  si todos los edittext tienen
     * datos validos entonces se retornara un  uno 1. tambien muestra mensaje de datos no validos a cada
     * edittext que tiene datos invalidos
     * @return valor 0 0 1.
     */
    public int espaciosblancos() {
        int r = 1;
        if ("".equals(txtnombreu.getText().toString())||0==espacios(txtnombreu.getText().toString())) {
            r = 0;
            txtnombreu.setHint("Debe ingresar un nombre de usuario");
            txtnombreu.setHintTextColor(Color.parseColor("#51FF1218"));
        }

        if ("".equals(txtnombre.getText().toString())||0==espacios(txtnombre.getText().toString())) {
            r = 0;
            txtnombre.setText("");
            txtnombre.setHint("Debe ngresar su nombre");
            txtnombre.setHintTextColor(Color.parseColor("#51FF1218"));//cambia a color rojo
        }

        if ("".equals(txtapellido.getText().toString())||0==espacios(txtapellido.getText().toString())) {
            r = 0;
            txtapellido.setText("");
            txtapellido.setHint("Debe ingresar su apellido");
            txtapellido.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtfecha.getText().toString())) {
            r = 0;
            txtfecha.setText("");
            txtfecha.setHint("Debe ingresar su fecha de nacimiento");
            txtfecha.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtcontra.getText().toString())) {
            r = 0;
            txtcontra.setText("");
            txtcontra.setHint("Debe ingresar una contraseña");
            txtcontra.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtcontrados.getText().toString())) {
            r = 0;
            txtcontrados.setText("");
            txtcontrados.setHint("Debe ingresar la contraseña");
            txtcontrados.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtrespuesta.getText().toString())||0==espacios(txtrespuesta.getText().toString())) {
            r = 0;
            txtrespuesta.setText("");
            txtrespuesta.setHint("Debe ingresar una respuesta acorde");
            txtrespuesta.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    /**
     * metodo que verifica que la fecha ingresada este en el rango permitido y en caso de que sea
     * correcta retorna un uno 1 y en caso contrario un cero 0. muestra mensaje en caso de que haya
     * error con la fecha
     * @return valor 1 o 0
     */
    public int compararfecha() {
        Calendar f = Calendar.getInstance();
        int año = f.get(Calendar.YEAR);
        String[] fecha = txtfecha.getText().toString().split("-");
        año = año - Integer.parseInt(fecha[2]);
        if (año > 15)
            return 1;
        else {
            txtfecha.setText("");
            txtfecha.setHint("Su Fecha Debe Ser Menor Al Año 2000");
            txtfecha.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo que compara las dos contraseñas ingresas, en caso de que las contraseñas no considan
     * se devolvera un entero  con el valor  cero 0, o en caso contrario se devolvera un uno 1.
     * @return
     */
    public int compararcontraseñas() {
        if (txtcontra.getText().toString().equals(txtcontrados.getText().toString()))
            return 1;
        else {
            txtcontrados.setText("");
            txtcontrados.setHint("Las contraseñas no coinciden, repitala");
            txtcontrados.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo que muestra mensaje
     * @param m variable string que lleva el  mensaje que se va a mostrar
     */

    public void mostrar(String m) {
        Toast.makeText(this, " mensaje " + m, Toast.LENGTH_LONG).show();
    }

    /**
     * metodo  que recive un string, y revisa si este string comienza por algun espacio en blanco " "
     * @param t variable que contiene el texto  a revisar
     * @return retorna 0 si el texto  inicia  con " " o  1 si inicia con algun otro  caracter diferente
     * al " "
     */
    public int  espacios(String t){
        if (t.charAt(0) == ' ')
            return 0;//retorna cero si el parametro  comienza con un espacio
        else
            return 1;//retorna uno  si el parametro  no comienza con un espacio
    }

    /**
     * este metodo hace referencia a un jbutton encargado de aumentar el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     * @param v
     */
    public void masET(View v){
        float t= texto.getTextSize();
        texto.setTextSize(t+4);
    }

    /**
     * este metodo hace referencia a un jbutton encargado de disminuir el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     * @param v
     */
    public void menosET(View v){
        float t= texto.getTextSize();
        texto.setTextSize(t-2);
    }
}
