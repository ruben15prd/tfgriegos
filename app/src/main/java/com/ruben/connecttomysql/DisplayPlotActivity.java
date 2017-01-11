package com.ruben.connecttomysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class DisplayPlotActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private Switch bombaAguaSw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_plot);


        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textViewNombrePlot);
        bombaAguaSw = (Switch) findViewById(R.id.switch1);

        //Obtenemos la farm pasada por parametro
        Plot plot =(Plot) getIntent().getSerializableExtra("plot");

        //Cargamos los valores a mostrar

        nombreTv.setText(plot.getName());


        //Log.d("Debug", "Antes de la consulta el usuario: " + plot.getWaterPump());


        if(plot.getWaterPump()==true){
            bombaAguaSw.setChecked(true);
        }else{
            bombaAguaSw.setChecked(false);
        }
        //Deshabilitamos el switch para que no se pueda usar
        bombaAguaSw.setEnabled(false);

        Button buttonEditarPlot = (Button) findViewById(R.id.buttonEditPlot);


        buttonEditarPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayPlotActivity.this, EditPlotActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);
            }
        });

        Button buttonAutoRiegoPlot = (Button) findViewById(R.id.buttonAutoRiego);


        buttonAutoRiegoPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayPlotActivity.this, DisplayClimatologicalProbeActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);
            }
        });

        Button buttonVerHistorico = (Button) findViewById(R.id.verHistoricoBt);
        buttonVerHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DisplayPlotActivity.this, HistoricoActivity.class);
                startActivity(intent);
            }
        });



    }
}
