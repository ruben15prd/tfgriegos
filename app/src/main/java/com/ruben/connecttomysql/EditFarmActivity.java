package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditFarmActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private EditText latitudEt;
    private EditText longitudEt;
    private Integer farmId;
    private Farm farm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farm);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editText3);
        latitudEt = (EditText) findViewById(R.id.editText4);
        longitudEt = (EditText) findViewById(R.id.editText5);

        //Obtenemos la farm pasada por parametro
        farm =(Farm) getIntent().getSerializableExtra("farm");

        //Cargamos los valores a mostrar

        nombreEt.setText(farm.getName());
        latitudEt.setText(farm.getLatitude().toString());
        longitudEt.setText(farm.getLongitude().toString());

        farmId= farm.getId();
        Log.d("Debug", "Farm id: " + farmId);
        Button buttonAceptar = (Button) findViewById(R.id.button3);


        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditarFarm().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarFarm extends AsyncTask<Void,Void,Void> {
        private String nombre="";
        private Double latitud;
        private Double longitud;

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> errores;


            errores = new ArrayList<String>();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());

                runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (Write your code here to run in UI thread)

                        //Obtenemos el texto de los campos que ha introducido el usuario
                        nombre = nombreEt.getText().toString();
                        latitud= Double.parseDouble(latitudEt.getText().toString());
                        longitud = Double.parseDouble(longitudEt.getText().toString());





                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = FarmUtils.compruebaErrores(EditFarmActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update FARM set name='"+nombre + "',latitude="+latitud+",longitude="+longitud+" where id="+farmId;
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);





                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            List<String> errores;

            // Comprobamos y visualizamos los errores en caso de que fuera necesario
            errores = FarmUtils.compruebaErrores(EditFarmActivity.this);
            FarmUtils.visualizaErrores(EditFarmActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditFarmActivity.this, ListFarmsActivity.class);

                //intent.putExtra("farm", farm);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }


    }

