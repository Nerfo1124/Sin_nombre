package co.com.udistrital.sin_nombre.view;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.FormulaDAO;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.util.Contador;
import co.com.udistrital.sin_nombre.util.ProgressCircle;
import co.com.udistrital.sin_nombre.util.pantalla_on_off;
import co.com.udistrital.sin_nombre.vo.FormulaVO;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

public class Principal extends AppCompatActivity {

    private static String TAG_LOG = "[Sin_nombre]";

    /**
     * Variable del Usuario que mantenien la sesion
     */
    private UsuarioVO usuarioSesion;
    int idUsuarioSesion,cont=1,aux,guardado;

    ProgressCircle progressCircle;
    Switch s1,s2;
    MyTask myTask=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ponerTiempo();
        cargarDatosIniciales();
        cargarSwitchLetra();
        cargarSwitchLetra2();
        Toast.makeText(this,"anterior "+aux+" actual "+idUsuarioSesion,Toast.LENGTH_LONG).show();
        if(aux==idUsuarioSesion){

        }else{
            guardarContador();
            HistoricoVO objH= new HistoricoVO();
            HistoricoDAO objBD = new HistoricoDAO(this);
            objH=objBD.consult2(idUsuarioSesion);
            if(objH==null){
                Log.e(TAG_LOG, "[1] ");
                Contador.horas=0;
                Contador.minutos=0;
                Contador.segundos=0;
            }else{
                Log.e(TAG_LOG, "[2] ");
                String v[]= objH.getTiempo().split(":");
                Contador.horas=Integer.parseInt(v[0]);
                Contador.minutos=Integer.parseInt(v[1]);
                Contador.segundos=Integer.parseInt(v[2]);
            }
        }
        cargarSwitchYProgress();
    }

    public void guardarContador(){
        HistoricoVO objH= new HistoricoVO();
        HistoricoVO objC= new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(this);
        objH.setIdUsuario(aux);
        objH.setTiempo(Contador.tiempo);
        objH.setFechaHistorico(new Date());
        objC=objBD.consult2(aux);
        if(objC==null)
            objBD.insert(objH);
        else
            objBD.update(objH);

    }

    public void cargarDatosIniciales(){
        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        guardarTiempo(idUsuarioSesion);
        Log.d(TAG_LOG, "Parametro recibido: " + idUsuarioSesion);

        //
        UsuarioDAO daoU=new UsuarioDAO(this);
        usuarioSesion = daoU.consult(idUsuarioSesion);
        getSupportActionBar().setTitle("BIENVENIDO: " + usuarioSesion.getNombreUsuario());
    }

    public void cargarSwitchLetra(){
        try {
            s1=(Switch)findViewById(R.id.letramanual);
            FormulaVO objF = new FormulaVO();
            FormulaDAO objBD = new FormulaDAO(getApplicationContext());
            objF = objBD.consult(idUsuarioSesion);
            s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(getApplicationContext(),"contador: "+cont,Toast.LENGTH_LONG).show();
                        if(cont!=0) {
                            bloquearSwitch();
                            s2.setChecked(false);
                            FormulaVO objF = new FormulaVO();
                            FormulaDAO objBD = new FormulaDAO(getApplicationContext());
                            objF = objBD.consult(idUsuarioSesion);
                            Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) Float.parseFloat(objF.getTamanioFuente()) / 40);
                            toast();
                        }else
                            cont=1;
                    } else {
                        bloquearSwitch();
                        Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) 1);
                        toast();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), " Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void cargarSwitchLetra2(){
        FormulaVO objF = new FormulaVO();
        FormulaDAO objBD = new FormulaDAO(getApplicationContext());
        objF = objBD.consult(idUsuarioSesion);
        s2=(Switch)findViewById(R.id.letraautomatica);
        revisarletra(objF);
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (isChecked) {
                        if (cont != 0) {
                            bloquearSwitch();
                            s1.setChecked(false);
                            FormulaVO objF = new FormulaVO();
                            FormulaDAO objBD = new FormulaDAO(getApplicationContext());
                            objF = objBD.consult(idUsuarioSesion);
                            Toast.makeText(getApplicationContext(), "OD: " + objF.getaVisualOD() + "OI: " + objF.getaVisualOI(), Toast.LENGTH_LONG).show();
                            //Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) Float.parseFloat(objF.getTamanioFuente()) / 40);
                            toast();
                        } else
                            cont = 1;
                    } else {
                        bloquearSwitch();
                        Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) 1);
                        toast();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), " Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void cargarSwitchYProgress(){
        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        progressCircle.startAnimation();
        Contador.setIdSesion(idUsuarioSesion);
        if(isMyServiceRunning(pantalla_on_off.class)==false) {
            startService(new Intent(this, pantalla_on_off.class));
            myTask = new MyTask();
            myTask.execute();
        }
        else {
            myTask = new MyTask();
            myTask.execute();
        }
    }

    public void revisarletra(FormulaVO o){
        try{
            Float s=Settings.System.getFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE);
            Toast.makeText(getApplicationContext(), "Letra sistema "+s+" tamaño usuario "+o.getTamanioFuente(), Toast.LENGTH_LONG).show();
            if(s==1){
                s1.setChecked(false);
                s2.setChecked(false);
            }else{
                if(s==Float.parseFloat(o.getTamanioFuente()) / 40){
                    cont=0;
                    s1.setChecked(true);
                }else{
                    cont=0;
                    if(false){
                        s2.setChecked(true);
                    }else
                        s2.setChecked(false);
                }
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), " Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void bloquearSwitch(){
        s1.setEnabled(false);
        s2.setEnabled(false);
    }
    public void toast(){
        Toast toast3 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.notificacion, (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Debe reiniciar su dispositivo para poder aplicar los cambios en el tamaño de letra");
        toast3.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.setView(layout);
        toast3.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.boton) {
            Toast.makeText(this, "Se presionó el ícono de la *", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.ejercicio) {
            Toast.makeText(this, "Se presionó la opción ejercicio", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.cuenta) {
            Intent perfil = new Intent(this, PerfilUsuario.class);
            perfil.putExtra("idUsuario", "" + usuarioSesion.getIdUsuario());
            startActivity(perfil);
        }
        if (id == R.id.modificar) {
            Intent modificar = new Intent(this, ModificacionDatos.class);
            modificar.putExtra("idUsuarioM", "" + idUsuarioSesion);
            startActivity(modificar);
        }
        if (id == R.id.cerrar) {
            Dialogo("¿Cerrar Sesion?", "\tDesea Cerrar Sesion?",0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir?")
                    .setMessage("No hay marcha atras")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            //Inicio.this.finish();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Dialogo(String tit, final String men, final int opc) {
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (opc == 0) {
                                 if (myTask!=null)
                                    myTask.onCancelled();
                                Intent i = new Intent(getApplicationContext(), InicioSesion.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Principal.this.finish();
                                guardarTiempo(idUsuarioSesion);
                            }
                        }
                    }).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error Inicio - Dialogo:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void seg(View v){
        try{
            Intent seg = new Intent(this, Seguimiento.class);
            seg.putExtra("idUsuario", "" + usuarioSesion.getIdUsuario());
            startActivity(seg);
        }catch (Exception e){
            Toast.makeText(this, "Error Inicio - Dialogo:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ponerTiempo() {
        try{
            SharedPreferences prefe=getSharedPreferences("usuario", Context.MODE_PRIVATE);
            aux=Integer.parseInt(prefe.getString("1234", "1"));
        }catch (Exception e){
            Toast.makeText(this, "Error!: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }

    public void guardarTiempo(int id) {
        SharedPreferences preferencias=getSharedPreferences("usuario",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("1234", ""+id);
        editor.commit();
    }


        private class MyTask extends AsyncTask<String, String, String> {

        public boolean cent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cent = true;
        }

        @Override
        protected String doInBackground(String... params) {
            while (cent) {
                try {
                    if (Contador.tiempo.toString() != "") {
                        String v[] = Contador.tiempo.split(":");
                        int minutos = (Integer.parseInt(v[0]) * 60) + (Integer.parseInt(v[1]));
                        float min = (float) minutos / 480;
                        publishProgress("" + min);
                    } else {
                        publishProgress("" + 0);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressCircle.setProgress(Float.parseFloat(values[0]));
            progressCircle.startAnimation();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent = false;
        }
    }

}
