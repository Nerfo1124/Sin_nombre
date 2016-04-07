package co.com.udistrital.sin_nombre.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.dao.SistemaDAO;
import co.com.udistrital.sin_nombre.view.Ejercicios;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;
import co.com.udistrital.sin_nombre.vo.SistemaVO;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Contador extends Thread {

    private static String TAG_LOG = "[Sin_nombre]";
    int notificationID = 1;
    static int frecuencia=0,band=0;

    public static int idUsuarioSesion;

    boolean continua=true,siempre=true;
    public static  int centesimas = 00,minutos=00, segundos=00, horas=0;
    public static String tiempo="";
    Context c;

    public Contador(Context c){
        this.c=c;
    }

    @SuppressWarnings("static-access")
    public void run() {
        try {
            while(siempre) {
                Log.e("Sin_nombre", "por FUERAAAAA hora:"+horas+" minutos:"+minutos+" segundos:"+segundos);
                if(ReiniciarContador()) {
                    Log.e("Sin_nombre", "FUERA - reinicio-> horas: "+horas+" minutos: "+minutos+" segundos: "+segundos);
                    this.sleep(60000);
                }

                while (continua) {
                    Log.e("Sin_nombre", "por DENNTROOOO hora:"+horas+" minutos:"+minutos+" segundos:"+segundos);
                    if(idUsuarioSesion==0){
                        idUsuarioSesion=BuscarUltimoUsuario1();
                        ponerfre(c);
                    }

                    if(ReiniciarContador()) {
                        this.sleep(60000);
                        Log.e("Sin_nombre", "DENTRO - reinicio-> horas: " + horas + " minutos: " + minutos + " segundos: " + segundos);
                    }

                    if(frecuencia<(horas*60+minutos)){
                        frecuencia=frecuencia+band;
                    }
                    if((frecuencia==(horas*60+minutos))&&segundos==0){
                        Log.e("Sin_nombre", "entro!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        this.displayNotification();
                        frecuencia=frecuencia+band;
                        //minutos++;
                        guardarfre(c);
                        ponerfre(c);
                    }
                    if (centesimas == 99) {
                        centesimas = 00;
                        segundos++;
                    }
                    if (segundos == 59){
                        segundos = 00;
                        minutos++;
                    }
                    if (minutos >= 59){
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
                this.sleep(1000);
            }
        }catch (Exception ex) {
            Log.d("Sin_nombre", "Error en la ejecucion del servicio", ex);
        }

    }

    public boolean ReiniciarContador(){
        Calendar c = Calendar.getInstance();
        //Log.e("[Prueba]", "Fecha "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
        if(c.get(Calendar.HOUR_OF_DAY)==23&&c.get(Calendar.MINUTE)==59) {
            try{
                HistoricoVO objH= new HistoricoVO();
                HistoricoVO objC= new HistoricoVO();
                HistoricoDAO objBD= new HistoricoDAO(this.c);
                objH.setTiempo(tiempo);
                objH.setFechaHistorico(new Date());
                objH.setIdUsuario(idUsuarioSesion);
                objC=objBD.consult2(idUsuarioSesion);
                if(objC==null)
                    objBD.insert(objH);
                else
                    objBD.update(objH);
                Log.d("Sin_nombre", " Tiempo " + tiempo + " fecha " + new Date()+ "id "+idUsuarioSesion);
                segundos = 0;
                minutos = 0;
                horas = 0;
                tiempo="0:0:0";
                guardarTiempo();
                return true;
            }catch(Exception ex) {
                Log.e(TAG_LOG, "Error en Reinciar Contador", ex);
            }
        }
        return false;
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de recibir el id de Sesion desde la actividad Principal.
     *
     * @param idSesion
     */
    public static void setIdSesion(int idSesion,Context c) {
        try{
            Log.d(TAG_LOG, "Valor de la Variable Sesion en Contador: " + idUsuarioSesion);
            idUsuarioSesion = idSesion;
            SistemaVO objS= new SistemaVO();
            SistemaDAO objBD = new SistemaDAO(c);
            objS=objBD.consult(idSesion);
            frecuencia= Integer.parseInt("" + objS.getFrecuencia());
            guardarfre(c);
            ponerfre(c);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error en  setIdsesion", e);
        }

    }

    protected void displayNotification(){
        try{
            Log.e(TAG_LOG, "[Sin_nombre] creando notificacion");
            Intent i = new Intent(c, Ejercicios.class);
            i.putExtra("notificationID", notificationID);

            PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, i, 0);

            NotificationManager nm = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);

            CharSequence ticker ="Sin-nombre: Debe realizar sus ejercicios";
            CharSequence contentTitle = "Sin-nombre - Ejercicios";
            CharSequence contentText = "Realiza ahora los ejercicios";
            Notification noti = new NotificationCompat.Builder(c)
                    .setContentIntent(pendingIntent)
                    .setTicker(ticker)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .addAction(R.mipmap.ic_launcher, ticker, pendingIntent)
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .build();
            nm.notify(notificationID, noti);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }

    public static void ponerfre(Context c) {
        try{
            SharedPreferences prefe= c.getSharedPreferences("frecuencia", Context.MODE_PRIVATE);
            frecuencia=Integer.parseInt(prefe.getString("aic", "-1"));
            band=frecuencia;
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    public static void guardarfre(Context c) {
        try{
            SharedPreferences preferencias=c.getSharedPreferences("frecuencia",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferencias.edit();
            editor.putString("aic", ""+frecuencia);
            editor.commit();
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }

    public void guardarTiempo() {
        try{
            SharedPreferences preferencias=c.getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferencias.edit();
            editor.putString("mail", tiempo);
            editor.commit();
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    public int BuscarUltimoUsuario1() {
        try{
            SharedPreferences prefe=c.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String v[]=prefe.getString("1234", "0:0").split(":");
            return Integer.parseInt(v[0]);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return 0;
    }

}

