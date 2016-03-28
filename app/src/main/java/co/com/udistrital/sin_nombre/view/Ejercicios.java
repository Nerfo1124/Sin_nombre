package co.com.udistrital.sin_nombre.view;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.EjercicioDAO;
import co.com.udistrital.sin_nombre.util.ArrayAdapter;
import co.com.udistrital.sin_nombre.vo.EjercicioVO;

public class Ejercicios extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "[Sin_nombre]";

    String[] names;
    ListView ejerciciosView;
    ArrayAdapter myArrayAdapter = null;

    List<EjercicioVO> ejercicioInfoList = null;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Ingresando a la vista Ejercicios.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        /*NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID"));*/

        mContext = this;

        ejerciciosView = (ListView) findViewById(R.id.listaEjercicios);
        ejerciciosView.setOnItemClickListener(this);

        try {
            EjercicioDAO dao = new EjercicioDAO(mContext);
            ejercicioInfoList = dao.list();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error durante la carga de los ejercicios.", e);
        }

        myArrayAdapter = new ArrayAdapter(mContext, ejercicioInfoList);
        ejerciciosView.setAdapter((ListAdapter) myArrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
