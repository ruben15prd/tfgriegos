package com.ruben.connecttomysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayFarmActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView latitudTv;
    private TextView longitudTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_farm);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView8);
        latitudTv = (TextView) findViewById(R.id.textView9);
        longitudTv = (TextView) findViewById(R.id.textView10);

        //Obtenemos la farm pasada por parametro
        Farm farm =(Farm) getIntent().getSerializableExtra("farm");

        //Cargamos los valores a mostrar

        nombreTv.setText(farm.getName());
        latitudTv.setText(farm.getLatitude().toString());
        longitudTv.setText(farm.getLongitude().toString());

        Button buttonEditarFarm = (Button) findViewById(R.id.button);


        buttonEditarFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayFarmActivity.this, EditFarmActivity.class);
                intent.putExtra("farm", farm);
                startActivity(intent);
            }
        });


    }



}
