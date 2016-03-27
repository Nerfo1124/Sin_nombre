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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        /*NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID"));*/

        mContext = this;

        ejerciciosView = (ListView) findViewById(R.id.listaEjercicios);
        ejerciciosView.setOnItemClickListener(this);

        EjercicioProccesor mytask = new EjercicioProccesor();
        mytask.execute("listExcercises.txt");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private class EjercicioProccesor extends AsyncTask<String, Void, Integer> {
        ProgressDialog progressDialog;
        int delay = 5000; // ms

        public EjercicioProccesor() {
            super();
        }

        // Se muestra un mensaje mientras en segundo plano se ejecuta la carga de los Ejercicios.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext, "Por favor espere!", "Cargando lista de ejercicios...");
        }

        // Este metodo se ejecuta en segundo plano y se encarga de cargar los Ejercicios.
        @Override
        protected Integer doInBackground(String... strings) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Error durante la ejecucion del metodo.");
            }
            // Carga de la lista de ejercicios.
            try {
                EjercicioDAO dao = new EjercicioDAO(mContext);
                ejercicioInfoList = dao.list();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error durante la carga de los ejercicios.", e);
            }

            return (Integer) 1;
        }

        // This method will be executed on the main UI thread and can access the UI and update
        // the listview. We dismiss the progress dialog after updating the listview.
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            myArrayAdapter = new ArrayAdapter(mContext, ejercicioInfoList);
            ejerciciosView.setAdapter((ListAdapter) myArrayAdapter);
            progressDialog.dismiss();
        }

        // Este metodo es llamado si se cancela el proceso en segundo plano.
        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }
}
