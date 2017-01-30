package com.ruben.connecttomysql.irrigation.manual;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.model.Manual;
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListManualActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Date cancelMoment;
    private Date startDate;
    private Integer duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manual);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<Manual> manuals;
        Statement st;


        manuals = new ArrayList<Manual>();

        Plot plot =(Plot) getIntent().getSerializableExtra("plot");


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
                int userId = ConnectionUtils.getUserId();
                String sql = "select * from IRRIGATION where id_plot="+plot.getId();
                //Realizamos la consulta contra la base de datos
                final ResultSet rs = st.executeQuery(sql);

                //rs.next();

                while(rs.next()) {
                    Manual manual;
                    id = rs.getInt(1);
                    name = rs.getString(4);
                    cancelMoment = rs.getDate(3);

                    String sql2 = "select * from MANUAL where id_irrigation="+id;
                    final ResultSet rs2 = st.executeQuery(sql);

                    startDate = rs2.getDate(3);
                    duration = rs2.getInt(4);

                    manual = new Manual(id, name, cancelMoment, startDate, duration);
                    //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());


                    manuals.add(manual);

                }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<Manual>(listView.getContext(),android.R.layout.simple_list_item_1,manuals);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Manual manual =(Manual) adapter.getItem(position);

                Intent intent = new Intent(ListManualActivity.this, DisplayManualActivity.class);
                intent.putExtra("manual", manual);
                startActivity(intent);
            }
        });



        Button buttonCreateManual = (Button) findViewById(R.id.button4);

        buttonCreateManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListManualActivity.this, CreateManualActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
