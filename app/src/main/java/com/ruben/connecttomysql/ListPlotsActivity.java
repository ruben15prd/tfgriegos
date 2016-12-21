package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListPlotsActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Boolean waterPump;
    private Integer idFarm2;
    private Plot plot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plots);


        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<Plot> plots;
        Statement st;


        plots = new ArrayList<Plot>();

        //Obtenemos la idFarm pasada por parametro
        Integer idFarm =(Integer) getIntent().getSerializableExtra("idFarm");

        //Log.d("Debug", "idFarm: "+ idFarm);

        listView =  (ListView) findViewById(R.id.listViewPlots);

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            String sql = "select * from PLOT where id_farm='"+idFarm+"'";
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                Plot plot;
                id = rs.getInt(1);
                name = rs.getString(3);
                waterPump = rs.getBoolean(4);
                idFarm2 = rs.getInt(5);

                plot = new Plot(id,name,waterPump,idFarm2);
                //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());


                plots.add(plot);

            }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<Plot>(listView.getContext(),android.R.layout.simple_list_item_1,plots);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                plot =(Plot) adapter.getItem(position);

                Intent intent = new Intent(ListPlotsActivity.this, DisplayPlotActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);
            }
        });

        Button buttonCreatePlot = (Button) findViewById(R.id.buttonCrearPlot);

        buttonCreatePlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListPlotsActivity.this, CreatePlotActivity.class);
                intent.putExtra("idFarm", idFarm);
                startActivity(intent);

            }
        });




    }
}
