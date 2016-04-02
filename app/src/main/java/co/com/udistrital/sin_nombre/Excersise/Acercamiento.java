package co.com.udistrital.sin_nombre.Excersise;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoExcDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoExcVO;

public class Acercamiento extends AppCompatActivity {

    ImageView imagen;
    Chronometer c;
    AnimationDrawable frameAnimation;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercamiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        c = (Chronometer) findViewById(R.id.chronometer);
        imagen = (ImageView) findViewById(R.id.imagen);
        imagen.setBackgroundResource(R.drawable.seguirlapiz);
        boton = (Button)findViewById(R.id.btngirar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void girar(View v) {
        try {
            boton.setEnabled(false);
            frameAnimation = (AnimationDrawable) imagen.getBackground();
            frameAnimation.start();
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    c.setText("Tiempo Restante: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    frameAnimation.stop();
                    HistoricoExcVO objE=new HistoricoExcVO();
                    HistoricoExcDAO objDB=new HistoricoExcDAO(getApplicationContext());
                    objE.setIdUsuario(BuscarUltimoUsuario1());
                    objE.setIdEjercicio(2);
                    objE.setFechaRegistro(new Date());
                    objDB.insert(objE);
                    Log.e("[Sin_nombre]", "HOLA: "+objE.getIdUsuario());
                    c.setText("FINALIZADO");
                    boton.setEnabled(true);
                }
            }.start();
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "Error: ", ex);
        }
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
