package co.com.udistrital.sin_nombre.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.util.Contador;
import co.com.udistrital.sin_nombre.util.ProgressCircle;
import co.com.udistrital.sin_nombre.util.pantalla_on_off;

public class Principal extends AppCompatActivity {

    ProgressCircle progressCircle;
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        int idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        Log.d("[Sin_nombre]", "Parametro recibido: " + idUsuarioSesion);

        Switch s=(Switch)findViewById( R.id.contador);
        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        progressCircle.startAnimation();
        myTask = new MyTask();
        myTask.execute();
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(new Intent(getApplicationContext(), pantalla_on_off.class));

                } else {
                    stopService(new Intent(getApplicationContext(), pantalla_on_off.class));
                }
            }
        });
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
                        float min = (float) minutos / 720;
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
