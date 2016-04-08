package co.com.udistrital.sin_nombre.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import co.com.udistrital.sin_nombre.view.InicioSesion;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Recevier extends BroadcastReceiver {

    Contador contador;
    Context context;
    pantalla_on_off p;

    public Recevier() {
    }

    public Recevier(Contador c, pantalla_on_off p){
        contador= c;
        this.p=p;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            this.context=context;
            //p.guardarTiempo();
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                contador.continua=true;
                //Toast.makeText(context, " ENCENDIDO "+contador.tiempo, Toast.LENGTH_LONG).show();
            } else
                contador.continua=false;

        }catch (Exception e){
            Log.e("[Sin_nombre]", " Error en Receiver: ", e);
        }

    }

}
