package co.com.udistrital.sin_nombre.util;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Contador extends Thread {


    boolean continua=true,siempre=true;
    int centesimas = 00,minutos=00, segundos=00, horas=00;
    public static String tiempo="";
    public DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");;
    public String date;
    String [] v;


    @SuppressWarnings("static-access")
    public void run() {
        while(siempre) {
         Calendar c=Calendar.getInstance();
            if(c.get(Calendar.HOUR_OF_DAY)==23&&c.get(Calendar.MINUTE)==59) {
                segundos=0;
                minutos=0;
                horas=0;
            }else{
                //Toast.makeText(null,"HOLA",Toast.LENGTH_LONG).show();
                while (continua) {
                    try {
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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}

