package co.com.udistrital.sin_nombre.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Contador extends Thread {

    private static String TAG_LOG = "[Sin_nombre]";

    boolean continua=true,siempre=true;
    int centesimas = 00,minutos=00, segundos=00, horas=00,band=0;
    public static String tiempo="";
    Context c;

    public Contador(Context c){
        this.c=c;
    }

    @SuppressWarnings("static-access")
    public void run() {
        try {
            while(siempre) {
                if(ReiniciarContador()){
                    this.sleep(60000);
                }
                while (continua) {
                    if(ReiniciarContador()){
                        this.sleep(60000);
                    }
                        if (centesimas == 99) {
                            centesimas = 00;
                            segundos++;
                        }
                        if (segundos == 59){
                            segundos = 00;
                            minutos++;
                        }
                        if (minutos == 59){
                            minutos = 00;
                            horas++;
                        }
                        centesimas++;
                        if(minutos<=9)
                            tiempo = horas + ":0" + minutos + ":" + segundos;
                        else
                            tiempo = horas + ":" + minutos + ":" + segundos;
                        if(segundos<=9)
                            tiempo = horas + ":" + minutos + ":0" + segundos;
                        else
                            tiempo = horas + ":" + minutos + ":" + segundos;
                        this.sleep(9);
                }
            }
        }catch (Exception ex) {
            Log.d("Sin_nombre", "Error en la ejecucion del servicio", ex);
        }

    }

    public boolean ReiniciarContador(){
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.HOUR_OF_DAY)==23&&c.get(Calendar.MINUTE)==59) {
            try{
                HistoricoVO objH= new HistoricoVO();
                HistoricoDAO objBD= new HistoricoDAO(this.c);
                objH.setTiempo(tiempo);
                objH.setFechaHistorico(new Date());
                objBD.insert(objH);
                Log.d(TAG_LOG, " Tiempo " + tiempo + " fecha " + new Date());
                segundos = 0;
                minutos = 0;
                horas = 0;
                return true;
            }catch(Exception ex) {
                Log.e(TAG_LOG, "Error en Reinciar Contador", ex);
            }
        }
        return false;
    }

}

