package com.ruben.connecttomysql;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);


        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<PlotData> plotDatas;
        Statement st;


        plotDatas = new ArrayList<PlotData>();

        //Obtenemos el plot pasado por parametro
        Plot plot =(Plot) getIntent().getSerializableExtra("plot");
        Integer plotId= plot.getId();

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            String sql = "select * from PLOTDATA where id_plot="+plotId;
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                Integer id = rs.getInt(1);
                Timestamp measuringMoment = rs.getTimestamp(3);
                Double soilMoisture = rs.getDouble(4);
                Double humidity = rs.getDouble(5);
                Double temperature = rs.getDouble(6);
                plotId = rs.getInt(7);

                PlotData plotData = new PlotData(id,measuringMoment,soilMoisture,humidity,temperature,plotId);
                //Log.d("Debug", "Temp: "+plotData.getMeasuringMoment());


                plotDatas.add(plotData);

            }



        }catch(Exception e){
            e.printStackTrace();
        }






        LineChart chart = (LineChart) findViewById(R.id.chart);



        ArrayList<Entry> entries1 = new ArrayList<>();


        ArrayList<String> labels1 = new ArrayList<String>();
        //Nos recorremos los plotData que hemos obtenido en la consulta

        Integer contador=0;
        for(PlotData pd: plotDatas){
            entries1.add(new Entry(pd.getSoilMoisture().floatValue(), contador));
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(pd.getMeasuringMoment());   // assigns calendar to given date

            Integer hora = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
            Integer minuto = calendar.get(Calendar.MINUTE);

            labels1.add(pd.getMeasuringMoment().toString());
            contador=contador+1;
        }


        LineDataSet dataset = new LineDataSet(entries1,"Temperatura");
        LineData data = new LineData(labels1, dataset);
        chart.setData(data);
        chart.setDescription("Tabla de temperaturas");
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        chart.animateY(2000);
    }







}
