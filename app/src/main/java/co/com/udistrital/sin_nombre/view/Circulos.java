package co.com.udistrital.sin_nombre.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoExcVO;


public class Circulos extends AppCompatActivity {

    ImageView imagen;
    int cont=0;
    MyTask myTask=null;
    Chronometer c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        c = (Chronometer)findViewById(R.id.chronometer);
        imagen = (ImageView)findViewById(R.id.imagen);

    }

    public void girar(View v){
        //myTask = new MyTask();
        //myTask.execute();
        new CountDownTimer(60000, 6000) {
            public void onTick(long millisUntilFinished) {
                c.setText("Tiempo Restante: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                c.setText("FINALIZADO");
            }
        }.start();
    }

    public void girar(int dir){
        if(dir==1){
            Animation giro;
            giro= AnimationUtils.loadAnimation(this, R.anim.girarder);
            giro.reset();
            imagen.startAnimation(giro);
        }else{
            Animation giro;
            giro= AnimationUtils.loadAnimation(this,R.anim.girarizq);
            giro.reset();
            imagen.startAnimation(giro);
        }
    }



    public void parar(){
        if(cont==10) {
            Log.e("[Prueba]", "parar");
            myTask.onCancelled();
            cont=0;
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
            if(myTask!=null)
                myTask.onCancelled();
            Intent intento = new Intent(this, Principal.class);
            intento.putExtra("idUsuario", "" +BuscarUltimoUsuario1());
            startActivity(intento);
            Circulos.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public int BuscarUltimoUsuario1() {
        try{
            SharedPreferences prefe=getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String v[]=prefe.getString("1234", "0:0").split(":");
            return Integer.parseInt(v[0]);
        }catch (Exception e){
            Toast.makeText(this, "Error!: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.e("eroro", "Error " + e.toString(), e);
        }
        return 0;
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
                    if(cont<10) {
                        publishProgress("" + cont);
                        Thread.sleep(6000);
                        Log.e("[Prueba]", "cont: "+cont);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            try{
                if(Integer.parseInt(values[0])<5){
                    girar(1);
                    cont++;
                } else{
                    cont++;
                    girar(2);
                }
                parar();
            }catch (Exception ex ){
                Log.e("[Error]", "error"+ex.getMessage().toString());
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
