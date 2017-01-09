package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayClimatologicalProbeActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView soilLowerLimit;
    private TextView soilUpperLimit;
    private TextView humidityLowerLimit;
    private TextView humidityUpperLimit;
    private TextView temperatureLowerLimit;
    private TextView temperatureUpperLimit;
    private Switch activeClimatologicalProbe;
    private ClimatologicalProbe cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_climatological_probe);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Instanciamos los elementos


        soilLowerLimit = (TextView) findViewById(R.id.textViewSoilLowerLimitTv);
        soilUpperLimit = (TextView) findViewById(R.id.textViewSoilUpperLimitTv);
        humidityLowerLimit = (TextView) findViewById(R.id.textViewHumidityLowerLimitTv);
        humidityUpperLimit = (TextView) findViewById(R.id.textViewHumidityUpperLimitTv);
        temperatureLowerLimit = (TextView) findViewById(R.id.textViewTemperatureLowerLimitTv);
        temperatureUpperLimit = (TextView) findViewById(R.id.textViewTemperatureUpperLimitTv);
        activeClimatologicalProbe = (Switch) findViewById(R.id.switchActiveClimatologicalProbe);

        Double soilLowerLimitDouble;
        Double soilUpperLimitDouble;
        Double humidityLowerLimitDouble;
        Double humidityUpperLimitDouble;
        Double temperatureLowerLimitDouble;
        Double temperatureUpperLimitDouble;
        Boolean active;



        //Obtenemos la farm pasada por parametro
        Plot plot =(Plot) getIntent().getSerializableExtra("plot");
        Integer plotId= plot.getId();



        Statement st;
        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            String sql = "select * from CLIMATOLOGICALPROBE where id_plot='"+plotId+"'";

            //Log.d("Debug", "Plot id: "+plotId);
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {

                Integer id;

                id= rs.getInt(1);
                soilLowerLimitDouble = rs.getDouble(3);
                soilUpperLimitDouble = rs.getDouble(6);
                humidityLowerLimitDouble = rs.getDouble(4);
                humidityUpperLimitDouble = rs.getDouble(7);
                temperatureLowerLimitDouble = rs.getDouble(5);
                temperatureUpperLimitDouble = rs.getDouble(8);
                active=rs.getBoolean(9);
                plotId= rs.getInt(10);

                if(active==true){
                    activeClimatologicalProbe.setChecked(true);
                }else{
                    activeClimatologicalProbe.setChecked(false);
                }
                //Deshabilitamos el switch para que no se pueda usar
                activeClimatologicalProbe.setEnabled(false);

                soilLowerLimit.setText(soilLowerLimitDouble.toString());
                soilUpperLimit.setText(soilUpperLimitDouble.toString());
                humidityLowerLimit.setText(humidityLowerLimitDouble.toString());
                humidityUpperLimit.setText(humidityUpperLimitDouble.toString());
                temperatureLowerLimit.setText(temperatureLowerLimitDouble.toString());
                temperatureUpperLimit.setText(temperatureUpperLimitDouble.toString());

                cp= new ClimatologicalProbe(id,soilLowerLimitDouble,soilUpperLimitDouble,humidityLowerLimitDouble,humidityUpperLimitDouble,temperatureLowerLimitDouble,temperatureUpperLimitDouble,active,plotId);
            }




        }catch(Exception e){
            e.printStackTrace();
        }

        Button buttonEditarAutoRiego = (Button) findViewById(R.id.buttonEditarClimatologicalProbe);


        buttonEditarAutoRiego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayClimatologicalProbeActivity.this, EditClimatologicalProbeActivity.class);
                intent.putExtra("climatologicalProbe", cp);
                startActivity(intent);
            }
        });

    }
}