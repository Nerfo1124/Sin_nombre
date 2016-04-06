package co.com.udistrital.sin_nombre.util;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class  pantalla_on_off extends Service {

    private static String TAG_LOG = "[Sin_nombre]";

    Contador contador=new Contador(this);
    public pantalla_on_off() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            contador.start();
            ponerTiempo();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            BroadcastReceiver mReceiver = new Recevier(contador,this);
            registerReceiver(mReceiver, filter);
            Toast.makeText(this, "Servicio creado!", Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "Servicio creado!");
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            contador.continua=false;
            guardarTiempo();
            // TODO: Return the communication channel to the service.
            Toast.makeText(this, "Servicio destruído!", Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "Servicio destruido!");
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void ponerTiempo() {
        try{
            SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
            String[] v = prefe.getString("mail", "0:0:0").split(":");
            contador.horas=Integer.parseInt(v[0]);
            contador.minutos=Integer.parseInt(v[1]);
            contador.segundos=Integer.parseInt(v[2]);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    public void guardarTiempo() {
        try{
            SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferencias.edit();
            editor.putString("mail", contador.tiempo);
            editor.commit();
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }
}
