package co.com.udistrital.sin_nombre.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;

public class Seguimiento extends AppCompatActivity {

    TextView texto;
    int idUsuarioSesion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        Bundle bundle = getIntent().getExtras();
        idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        texto=(TextView)findViewById(R.id.fecha);

        HistoricoVO objH= new HistoricoVO();
        HistoricoDAO objBD= new HistoricoDAO(this);
        objH=objBD.consult(idUsuarioSesion);
        texto.setText(""+objH.getTiempo());
    }
}
