package co.com.udistrital.sin_nombre.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
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
}
