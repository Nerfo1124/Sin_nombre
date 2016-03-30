package co.com.udistrital.sin_nombre.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Recevier extends BroadcastReceiver {

    Contador contador;
    Context context;
    pantalla_on_off p;

    public Recevier(Contador c, pantalla_on_off p){
        contador= c;
        this.p=p;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        p.guardarTiempo();
        p.ponerTiempo();
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            contador.continua=true;
            Toast.makeText(context, " ENCENDIDO "+contador.tiempo, Toast.LENGTH_LONG).show();
        }
        else {
            contador.continua=false;
            //Toast.makeText(context, " APAGADO -"+contador.v[0]+"-"+contador.v[1]+"-"+contador.v[2] , Toast.LENGTH_LONG).show();
        }
    }

}
