package co.com.udistrital.sin_nombre.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.R;

/**
 * Created by Rolando Baron on 21/09/2015.
 */
public class inicio_sesion extends AppCompatActivity {

    private EditText txtusuario,txtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportActionBar().setTitle("Inicio Sesion");
        //getSupportActionBar().setIcon(R.drawable.icono_home);
        txtusuario = (EditText)findViewById(R.id.txtusuario);
        txtpassword = (EditText)findViewById(R.id.txtcontraseña);
    }
    //metodo para generar nueva actividad
    public void registrar(View v){
        Intent intento= new Intent(getApplicationContext(),Registro.class);
        startActivity(intento);
    }

    public void olvido(View v){
        final Dialog personal = new Dialog(this);
        personal.setContentView(R.layout.recuperar_cuenta);
        personal.setTitle("\tRecuperar Cuenta");
        final EditText txtusuario= (EditText)personal.findViewById(R.id.txtnusuario);
        final EditText txtrespuesta= (EditText)personal.findViewById(R.id.txtrespuesta);
        final TextView txtpregunta = (TextView)personal.findViewById(R.id.txtpregunta);
        Button btnbuscar = (Button)personal.findViewById(R.id.btnbuscar);
        final Button btnenviar = (Button)personal.findViewById(R.id.btnenviar);
        btnenviar.setEnabled(false);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r= Buscar(txtusuario.getText().toString());
                if(r==" -1 "){
                    txtusuario.setText("");
                    txtusuario.setHint("No se encontro ese usuario");

                }else {
                    if(r==" -2 ")
                        txtusuario.setHint("Debe digitar un usuario");
                    else {
                        txtpregunta.setText(r);
                        btnenviar.setEnabled(true);
                        txtusuario.setEnabled(false);
                    }
                }
            }
        });

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtrespuesta.getText().toString().equals("")){
                    txtrespuesta.setHint("Debe digitar una respuesta");
                }else {
                    if(txtrespuesta.getText().toString().equals(Respuesta(txtusuario.getText().toString()))){
                        Toast.makeText(getApplicationContext(), "Cambiar Contraseña", Toast.LENGTH_SHORT).show();
                    }else{
                        txtrespuesta.setText("");
                        txtrespuesta.setHint("Respuesta Incorrecta");
                    }

                }
            }
        });

        personal.show();

    }

}
