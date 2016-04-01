package co.com.udistrital.sin_nombre.view;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import co.com.udistrital.sin_nombre.Excersise.Acercamiento;
import co.com.udistrital.sin_nombre.Excersise.Circulos;
import co.com.udistrital.sin_nombre.Excersise.LejosCerca;
import co.com.udistrital.sin_nombre.Excersise.Masaje;
import co.com.udistrital.sin_nombre.Excersise.Palmeo;
import co.com.udistrital.sin_nombre.Excersise.Parpadeo;
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position + 1) {
            case 1:
                Intent mIntent1 = new Intent(this, Parpadeo.class);
                startActivity(mIntent1);
                break;
            case 2:
                Intent mIntent2 = new Intent(this, Acercamiento.class);
                startActivity(mIntent2);
                break;
            case 3:
                Intent mIntent3 = new Intent(this, Circulos.class);
                startActivity(mIntent3);
                break;
            case 4:
                Intent mIntent4 = new Intent(this, Masaje.class);
                startActivity(mIntent4);
                break;
            case 5:
                Intent mIntent5 = new Intent(this, LejosCerca.class);
                startActivity(mIntent5);
                break;
            case 6:
                Intent mIntent6 = new Intent(this, Palmeo.class);
                startActivity(mIntent6);
                break;
            case 7:
                Log.d(LOG_TAG, "Ejercicio sin asignar...");
                break;
            case 8:
                Log.d(LOG_TAG, "Ejercicio sin asignar...");
                break;
            case 9:
                Log.d(LOG_TAG, "Ejercicio sin asignar...");
                break;
            case 10:
                Log.d(LOG_TAG, "Ejercicio sin asignar...");
                break;
        }
    }


}
