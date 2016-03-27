package co.com.udistrital.sin_nombre.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;

public class Seguimiento extends AppCompatActivity {

    int idUsuarioSesion;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    float v[]={0,0,0,0,0,0,0};
    GraphView graph;
    Date d7, d6, d5, d4, d3, d2,d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        try{
            Bundle bundle = getIntent().getExtras();
            idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
            cargardatos();
            grafica();
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[onCreate] Error en Seguimiento: " + e.toString(), e);
        }
    }

    public void cargardatos(){
        try{
            Calendar calendar = Calendar.getInstance();
            d7 = retrocederfecha(calendar);
            d6 = retrocederfecha(calendar);
            d5 = retrocederfecha(calendar);
            d4 = retrocederfecha(calendar);
            d3 = retrocederfecha(calendar);
            d2 = retrocederfecha(calendar);
            d1 = retrocederfecha(calendar);
            consulta(d7,6);
            consulta(d6,5);
            consulta(d5,4);
            consulta(d4,3);
            consulta(d3,2);
            consulta(d2,1);
            consulta(d1,0);
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[Cargardatos] Error en Seguimiento: " + e.toString(), e);
        }
    }

    public void grafica(){

        graph= (GraphView) findViewById(R.id.graph);
        graph.setTitle("Historico Uso");
        graph.setTitleTextSize(50);
        graph.setTitleColor(Color.GREEN);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, v[0]),
                new DataPoint(d2, v[1]),
                new DataPoint(d3, v[2]),
                new DataPoint(d4, v[3]),
                new DataPoint(d5, v[4]),
                new DataPoint(d6, v[5]),
                new DataPoint(d7, v[6])

        });
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setTextSize(1);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMaxY(1);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d7.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

    }

    public Date retrocederfecha(Calendar calendar){
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public void consulta(Date dia,int pos){
        try {
            HistoricoVO objH = new HistoricoVO();
            HistoricoDAO objBD = new HistoricoDAO(this);
            objH = objBD.consultIdFecha(idUsuarioSesion, dia);
            if (objH == null)
                v[pos] = 0;
            else{
                String s[]=objH.getTiempo().split(":");
                float min= Float.parseFloat(s[0])+Float.parseFloat(s[1])/60;
                Log.e("[Sin_nombre]", "[consulta] min: " + min);
                v[pos] = min;
            }
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[consulta] Error en Seguimiento: " + e.toString(), e);
        }
    }

    public void insertar(View v){
        HistoricoVO objH = new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(this);
        objH.setIdUsuario(idUsuarioSesion);
        objH.setFechaHistorico(d1);
        objH.setTiempo("0:10:50");
        objBD.insert(objH);
        objH.setFechaHistorico(d2);
        objH.setTiempo("0:20:15");
        objBD.insert(objH);
        objH.setFechaHistorico(d3);
        objH.setTiempo("0:15:45");
        objBD.insert(objH);
        objH.setFechaHistorico(d4);
        objH.setTiempo("0:30:15");
        objBD.insert(objH);
        objH.setFechaHistorico(d5);
        objH.setTiempo("0:45:00");
        objBD.insert(objH);
        objH.setFechaHistorico(d6);
        objH.setTiempo("0:1:15");
        objBD.insert(objH);



    }
}
