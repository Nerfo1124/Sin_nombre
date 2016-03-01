package co.com.udistrital.sin_nombre.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.EditText;

import co.com.udistrital.sin_nombre.dao.UsuarioDAO;

/**
 * Created by Fernando on 29/02/2016.
 */
public class PerfilUsuario extends AppCompatActivity {

    private EditText txtNameUsuario, txtFechaUsuario, txtSexo, txtIdSesion;
    private UsuarioDAO userDao = new UsuarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
