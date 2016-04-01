package co.com.udistrital.sin_nombre.Excersise;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import co.com.udistrital.sin_nombre.R;

public class Palmeo extends AppCompatActivity {


    ImageView imagen;
    Chronometer c;
    AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palmeo);
        c = (Chronometer) findViewById(R.id.chronometer);
        imagen = (ImageView) findViewById(R.id.imagen);
        imagen.setBackgroundResource(R.drawable.palmeo);
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
            frameAnimation = (AnimationDrawable) imagen.getBackground();
            frameAnimation.start();
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    c.setText("Tiempo Restante: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    c.setText("FINALIZADO");
                    frameAnimation.stop();
                }
            }.start();
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "Error: ", ex);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
