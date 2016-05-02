package co.com.udistrital.sin_nombre.Excersise;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoExcDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoExcVO;

public class LejosCerca extends AppCompatActivity {

    ImageView imagen;
    Chronometer c;
    AnimationDrawable frameAnimation;
    Button boton;
    CountDownTimer desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lejos_cerca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ejercicio - LejosCerca");
        try{
            c = (Chronometer) findViewById(R.id.chronometer);
            imagen = (ImageView) findViewById(R.id.imagen);
            imagen.setBackgroundResource(R.drawable.lejoscerca);
            boton = (Button)findViewById(R.id.btngirar);
        }catch (Exception e){
            Log.e("[Sin_nombre]", "Error " + e.toString(), e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            desc.cancel();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void girar(View v) {
        try {
            boton.setEnabled(false);
            frameAnimation = (AnimationDrawable) imagen.getBackground();
            frameAnimation.start();
            desc=new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    c.setText("Tiempo Restante: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    frameAnimation.stop();
                    HistoricoExcVO objE=new HistoricoExcVO();
                    HistoricoExcDAO objDB=new HistoricoExcDAO(getApplicationContext());
                    objE.setIdUsuario(Integer.parseInt(getDatosUsuario()));
                    objE.setIdEjercicio(5);
                    objE.setFechaRegistro(new Date());
                    objDB.insert(objE);
                    Log.e("[Sin_nombre]", "HOLA: " + objE.getIdUsuario());
                    c.setText("FINALIZADO");
                    boton.setEnabled(true);
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.notificacion);
                    mp.start();
                }
            };
            desc.start();
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "Error: ", ex);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            desc.cancel();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getDatosUsuario() {
        try {
            SharedPreferences prefe = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Datos", "0:0:0").split(":");
            return v[0];
        } catch (Exception e) {
            Log.e("[Error]", "Error " + e.toString(), e);
        }
        return null;
    }
}
