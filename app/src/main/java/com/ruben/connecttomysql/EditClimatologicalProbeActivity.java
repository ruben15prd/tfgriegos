package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditClimatologicalProbeActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText editTextSoilLower;
    private EditText editTextSoilUpper;
    private EditText editTextHumidityLower;
    private EditText editTextHumidityUpper;
    private EditText editTextTemperatureLower;
    private EditText editTextTemperatureUpper;
    private Switch switchActiveClimatologicalProbeSw;
    private Integer id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_climatological_probe);




        //Instanciamos los elementos
        editTextSoilLower = (EditText) findViewById(R.id.editTextSoilLower);
        editTextSoilUpper = (EditText) findViewById(R.id.editTextSoilUpper);
        editTextHumidityLower = (EditText) findViewById(R.id.editTextHumidityLower);
        editTextHumidityUpper = (EditText) findViewById(R.id.editTextHumidityUpper);
        editTextTemperatureLower = (EditText) findViewById(R.id.editTextTemperatureLower);
        editTextTemperatureUpper = (EditText) findViewById(R.id.editTextTemperatureUpper);
        switchActiveClimatologicalProbeSw = (Switch) findViewById(R.id.switchActiveClimatologicalProbe2);

        //Obtenemos el climatologicalProbe pasada por parametro
        ClimatologicalProbe climatologicalProbe =(ClimatologicalProbe) getIntent().getSerializableExtra("climatologicalProbe");

        id= climatologicalProbe.getId();

        //Cargamos los valores a mostrar

        editTextSoilLower.setText(climatologicalProbe.getSoilLowerLimit().toString());
        editTextSoilUpper.setText(climatologicalProbe.getSoilUpperLimit().toString());
        editTextHumidityLower.setText(climatologicalProbe.getHumidityLowerLimit().toString());
        editTextHumidityUpper.setText(climatologicalProbe.getHumidityUpperLimit().toString());
        editTextTemperatureLower.setText(climatologicalProbe.getTemperatureLowerLimit().toString());
        editTextTemperatureUpper.setText(climatologicalProbe.getTemperatureUpperLimit().toString());




        if(climatologicalProbe.getActive()==true){
            switchActiveClimatologicalProbeSw.setChecked(true);
        }else{
            switchActiveClimatologicalProbeSw.setChecked(false);
        }



        Button buttonAceptar = (Button) findViewById(R.id.buttonAceptarClimatologicalProbe);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                new EditClimatologicalProbeActivity.EditarClimatologicalProbe().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarClimatologicalProbe extends AsyncTask<Void,Void,Void> {
        private Double soilMoistureLower;
        private Double soilMoistureUpper;
        private Double humidityLower;
        private Double humidityUpper;
        private Double temperatureLower;
        private Double temperatureUpper;
        private Boolean active;


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
                        soilMoistureLower = Double.parseDouble(editTextSoilLower.getText().toString());
                        soilMoistureUpper = Double.parseDouble(editTextSoilUpper.getText().toString());
                        humidityLower = Double.parseDouble(editTextHumidityLower.getText().toString());
                        humidityUpper = Double.parseDouble(editTextHumidityUpper.getText().toString());
                        temperatureLower = Double.parseDouble(editTextTemperatureLower.getText().toString());
                        temperatureUpper = Double.parseDouble(editTextTemperatureUpper.getText().toString());


                        if(switchActiveClimatologicalProbeSw.isChecked()==true){
                            active=true;
                        }else{
                            active=false;
                        }


                        //Log.d("Debug", "soil run: " + soilMoistureLower);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = ClimatologicalProbeUtils.compruebaErrores(EditClimatologicalProbeActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                String sql = "update CLIMATOLOGICALPROBE set soilMoistureIrrigationLowerLimit="+soilMoistureLower + ",humidityIrrigationLowerLimit="+humidityLower+",temperatureIrrigationLowerLimit="+temperatureLower+",soilMoistureIrrigationUpperLimit="+soilMoistureUpper+",humidityIrrigationUpperLimit="+humidityUpper+",temperatureIrrigationUpperLimit="+temperatureUpper+",active="+active+" where id="+id;



                Log.d("Debug", "Cadena consulta: "+sql);
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

            errores = ClimatologicalProbeUtils.compruebaErrores(EditClimatologicalProbeActivity.this);
            ClimatologicalProbeUtils.visualizaErrores(EditClimatologicalProbeActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditClimatologicalProbeActivity.this, ListFarmsActivity.class);

                startActivity(intent);
            }




            super.onPostExecute(result);
        }


    }
    }

