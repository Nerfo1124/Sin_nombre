package co.com.udistrital.sin_nombre.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.sin_nombre.R;
import co.com.udistrital.sin_nombre.dao.HistoricoDAO;
import co.com.udistrital.sin_nombre.vo.HistoricoVO;

public class Seguimiento extends AppCompatActivity {

    int idUsuarioSesion=0;
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        Bundle bundle = getIntent().getExtras();
        idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        grafica();
        HistoricoVO objH= new HistoricoVO();
        HistoricoDAO objBD= new HistoricoDAO(this);
        objH=objBD.consult(idUsuarioSesion);
    }

    public void grafica(){
        Calendar calendar = Calendar.getInstance();
        Date d7 = retrocederfecha(calendar);
        Date d6 = retrocederfecha(calendar);
        Date d5 = retrocederfecha(calendar);
        Date d4 = retrocederfecha(calendar);
        Date d3 = retrocederfecha(calendar);
        Date d2 = retrocederfecha(calendar);
        Date d1 = retrocederfecha(calendar);


        graph= (GraphView) findViewById(R.id.graph);
        graph.setTitle("Historico Uso");
        graph.setTitleTextSize(50);
        graph.setTitleColor(Color.GREEN);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 2),
                new DataPoint(d3, 3),
                new DataPoint(d4, 4),
                new DataPoint(d5, 5),
                new DataPoint(d6, 6),
                new DataPoint(d7, 7)

        });
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d7.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

    }

    public Date retrocederfecha(Calendar calendar){
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public String [] consulta(){
        return  null;
    }
}
