package co.com.udistrital.sin_nombre;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import co.com.udistrital.sin_nombre.view.InicioSesion;

public class Reinicio extends BroadcastReceiver {
    public Reinicio() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            try{
                Log.e("[Sin_nombre]", "YESSSS" + context.getApplicationContext());
                Intent i = new Intent(context, InicioSesion.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }catch (Error e){
                Log.e("[Sin_nombre]", "error: "+e.getMessage(),e);
            }
        }
    }
}
