package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule.momentDay;

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
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.model.IrrigationMomentDay;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ListMomentDayActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private Timestamp irrigationMoment;
    private Integer duration;
    private Integer idIrrigation;
    private Integer idSeveralTimesDaysSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_moment_day);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<IrrigationMomentDay> riegos;
        Statement st;


        riegos = new ArrayList<IrrigationMomentDay>();

        Integer idIrrigation =(Integer) getIntent().getSerializableExtra("idIrrigation");


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();

            //String sql = "select * from IRRIGATIONMOMENTDAY where id_severalTimesDaySchedule ="+idIrrigation;

            String sql = "select * from IRRIGATIONMOMENTDAY where id_severalTimesDaySchedule =  (select id from SEVERALTIMESDAYSCHEDULE where id_irrigation ="+idIrrigation+ ")";
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                IrrigationMomentDay riego;
                id = rs.getInt(1);
                irrigationMoment = rs.getTimestamp(3);
                duration = rs.getInt(4);
                idSeveralTimesDaysSchedule = rs.getInt(5);
                riego = new IrrigationMomentDay(id, irrigationMoment, duration, idSeveralTimesDaysSchedule, idIrrigation);
                //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());

                riegos.add(riego);

            }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<IrrigationMomentDay>(listView.getContext(),android.R.layout.simple_list_item_1,riegos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                IrrigationMomentDay riego =(IrrigationMomentDay) adapter.getItem(position);

                Intent intent = new Intent(ListMomentDayActivity.this, DisplayMomentDayActivity.class);
                intent.putExtra("riego", riego);
                startActivity(intent);
            }
        });



        Button buttonCreate = (Button) findViewById(R.id.button4);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListMomentDayActivity.this, CreateMomentDayActivity.class);
                intent.putExtra("idSeveralTimesDaysSchedule", idSeveralTimesDaysSchedule);
                intent.putExtra("idIrrigation", idIrrigation);
                startActivity(intent);

            }

        });

    }




}
