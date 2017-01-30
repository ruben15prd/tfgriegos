package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.irrigation.severalTimesDaySchedule.momentDay.ListMomentDayActivity;
import com.ruben.connecttomysql.model.SeveralTimesDaySchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

public class DisplaySeveralTimesDayScheduleActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView cancelMomentTv;
    private TextView startDateTv;
    private TextView endDateTv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_several_times_day_schedule);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView29);
        cancelMomentTv = (TextView) findViewById(R.id.textView30);
        startDateTv = (TextView) findViewById(R.id.textView31);
        endDateTv = (TextView) findViewById(R.id.textView32);

        //Obtenemos la farm pasada por parametro
        SeveralTimesDaySchedule riego =(SeveralTimesDaySchedule) getIntent().getSerializableExtra("riego");

        //Cargamos los valores a mostrar

        nombreTv.setText(riego.getName());
        cancelMomentTv.setText(riego.getCancelMoment().toString());
        startDateTv.setText(riego.getStartDate().toString());
        endDateTv.setText(riego.getEndDate().toString());

        Button buttonCancelar = (Button) findViewById(R.id.button11);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date(System.currentTimeMillis()-100);
                riego.setCancelMoment(now);

                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update IRRIGATION set cancelMoment ='"+now +"'where id="+riego.getId();
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button buttonMomentDay= (Button) findViewById(R.id.button12);

        buttonMomentDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySeveralTimesDayScheduleActivity.this, ListMomentDayActivity.class);
                intent.putExtra("idIrrigation", riego.getId());
                startActivity(intent);
            }
        });
    }



}
