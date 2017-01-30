package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule;

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
import com.ruben.connecttomysql.model.SeveralTimesDaySchedule;
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListSeveralTimesDayScheduleActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Date cancelMoment;
    private Date startDate;
    private Date endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_several_times_day_schedule);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<SeveralTimesDaySchedule> riegos;
        Statement st;


        riegos = new ArrayList<SeveralTimesDaySchedule>();

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
                SeveralTimesDaySchedule riego;
                id = rs.getInt(1);
                name = rs.getString(4);
                cancelMoment = rs.getDate(3);

                String sql2 = "select * from SEVERALTIMESSCHEDULE where id_irrigation="+id;
                final ResultSet rs2 = st.executeQuery(sql);

                startDate = rs2.getDate(3);
                endDate = rs2.getDate(4);

                riego = new SeveralTimesDaySchedule(id, name, cancelMoment, startDate, endDate);
                //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());


                riegos.add(riego);

            }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<SeveralTimesDaySchedule>(listView.getContext(),android.R.layout.simple_list_item_1,riegos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                SeveralTimesDaySchedule riego =(SeveralTimesDaySchedule) adapter.getItem(position);

                Intent intent = new Intent(ListSeveralTimesDayScheduleActivity.this, DisplaySeveralTimesDayScheduleActivity.class);
                intent.putExtra("riego", riego);
                startActivity(intent);
            }
        });



        Button buttonCreateSeveralTimesSchedule = (Button) findViewById(R.id.button4);

        buttonCreateSeveralTimesSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListSeveralTimesDayScheduleActivity.this, CreateSeveralTimesDayScheduleActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
