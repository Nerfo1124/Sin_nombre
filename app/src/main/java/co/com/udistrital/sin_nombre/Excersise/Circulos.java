package co.com.udistrital.sin_nombre.Excersise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoExcDAO;
import co.com.udistrital.sin_nombre.view.Principal;
import co.com.udistrital.sin_nombre.vo.HistoricoExcVO;


public class Circulos extends AppCompatActivity {

    ImageView imagen;
    int cont = 0;
    Chronometer c;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        c = (Chronometer) findViewById(R.id.chronometer);
        imagen = (ImageView) findViewById(R.id.imagen);
        boton = (Button)findViewById(R.id.btngirar);
        Log.e("[Prueba]", "Inicia " + cont);
    }

    public void girar(View v) {
        boton.setEnabled(false);
        new CountDownTimer(57000, 5600) {
            public void onTick(long millisUntilFinished) {
                Log.e("[Prueba]", "seg: "+millisUntilFinished);
                c.setText("Vueltas Restantes: " + (10 - cont));
                if(cont<5){
                    Animation giro;
                    giro = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.girarder);
                    giro.reset();
                    imagen.startAnimation(giro);
                    cont++;
                }else{
                    if(cont<10){
                        Animation giro;
                        giro = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.girarizq);
                        giro.reset();
                        imagen.startAnimation(giro);
                        cont++;
                    }else
                        cont=0;

                }
            }

            public void onFinish() {
                c.setText("FINALIZADO");
                cont=0;
                HistoricoExcVO objE=new HistoricoExcVO();
                HistoricoExcDAO objDB=new HistoricoExcDAO(getApplicationContext());
                objE.setIdUsuario(BuscarUltimoUsuario1());
                objE.setIdEjercicio(3);
                objE.setFechaRegistro(new Date());
                objDB.insert(objE);
                Log.e("[Sin_nombre]", "HOLA: "+objE.getIdUsuario());
                c.setText("FINALIZADO");
                boton.setEnabled(true);
            }
        }.start();
    }
    public int BuscarUltimoUsuario1() {
        try{
            SharedPreferences prefe=getSharedPreferences("usuario", Context.MODE_PRIVATE);
            Log.d("[Sin_nombre]", "SharedPreferences: " + prefe.toString());
            String v[]=prefe.getString("1234", "0:0").split(":");
            return Integer.parseInt(v[0]);
        }catch (Exception e){
            Toast.makeText(this, "Error!: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.e("[Sin_nombre]", "Error " + e.toString(), e);
        }
        return -1;

    }

    public void girar(int dir) {
        if (dir == 1) {
            Animation giro;
            giro = AnimationUtils.loadAnimation(this, R.anim.girarder);
            giro.reset();
            imagen.startAnimation(giro);
        } else {
            Animation giro;
            giro = AnimationUtils.loadAnimation(this, R.anim.girarizq);
            giro.reset();
            imagen.startAnimation(giro);
        }
    }


    public void parar() {
        if (cont == 10) {
            Log.e("[Prueba]", "parar");
            //myTask.onCancelled();
            cont = 0;
            //HistoricoExcVO objHE0 = new HistoricoExcVO();
            //HistoricoDAO objBD = new HistoricoDAO(this);
            //objHE0.setIdUsuario(BuscarUltimoUsuario1());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           // if (myTask != null)
             //   myTask.onCancelled();
            Circulos.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyTask extends AsyncTask<String, String, String> {

        public boolean cent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cent = true;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("[Prueba]", "antes: " + cont);
            while (cent) {
                try {
                    Log.e("[Prueba]", "des: " + cont);
                    if (cont < 10) {
                        publishProgress("" + cont);
                        Thread.sleep(6000);
                        Log.e("[Prueba]", "cont: " + cont);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            try {
                if (Integer.parseInt(values[0]) < 5) {
                    girar(1);
                    cont++;
                } else {
                    cont++;
                    girar(2);
                }
                parar();
            } catch (Exception ex) {
                Log.e("[Error]", "error" + ex.getMessage().toString());
                parar();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent = false;
        }
    }
}
