package co.com.udistrital.sin_nombre.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.util.Contador;
import co.com.udistrital.sin_nombre.util.ProgressCircle;

public class Principal extends AppCompatActivity {

    ProgressCircle progressCircle;
    MyTask myTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        progressCircle.startAnimation();
        myTask = new MyTask();
        myTask.execute();
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
            //Intent intento  = new Intent(getApplicationContext(),Micuenta.class);
            //intento.putExtra("1 2 3", dato);
            //startActivity(intento);
            return true;
        }
        if (id == R.id.cerrar) {
            //Dialogo("¿Cerrar Sesion?","\tDesea Cerrar Sesion?",0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, String, String> {

        private boolean cent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cent = true;
        }

        @Override
        protected String doInBackground(String... params) {
            while (cent){
                try {
                    if (Contador.tiempo.toString()!=""){
                        String v[] = Contador.tiempo.split(":");
                        int minutos = (Integer.parseInt(v[0]) * 60) + (Integer.parseInt(v[1]));
                        float min= (float)minutos/720;
                        publishProgress(""+min);
                    } else{
                        publishProgress(""+0);
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
