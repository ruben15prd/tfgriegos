package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListFarmsActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Double longitude;
    private Double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_farms);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<Farm> farms;
        Statement st;


        farms = new ArrayList<Farm>();


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
                int userId = ConnectionUtils.getUserId();
                String sql = "select * from FARM where id_propietary="+userId;
                //Realizamos la consulta contra la base de datos
                final ResultSet rs = st.executeQuery(sql);

                //rs.next();

                while(rs.next()) {
                    Farm farm;
                    id = rs.getInt(1);
                    name = rs.getString(3);
                    longitude = rs.getDouble(4);
                    latitude = rs.getDouble(5);

                    farm = new Farm(id,name,longitude,latitude);
                    //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());


                    farms.add(farm);

                }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<Farm>(listView.getContext(),android.R.layout.simple_list_item_1,farms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Farm farm =(Farm) adapter.getItem(position);

                Intent intent = new Intent(ListFarmsActivity.this, DisplayFarmActivity.class);
                intent.putExtra("farm", farm);
                startActivity(intent);
            }
        });



        Button buttonCreateFarm = (Button) findViewById(R.id.button4);

        buttonCreateFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListFarmsActivity.this, CreateFarmActivity.class);
                startActivity(intent);

            }
        });

    }




}
