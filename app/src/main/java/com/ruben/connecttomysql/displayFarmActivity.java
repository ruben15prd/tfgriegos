package com.ruben.connecttomysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }
}
