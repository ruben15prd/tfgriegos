package com.ruben.connecttomysql;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 23/12/2016.
 */

public class ClimatologicalProbeUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText editTextSoilLower;
        EditText editTextSoilUpper;
        EditText editTextHumidityLower;
        EditText editTextHumidityUpper;
        EditText editTextTemperatureLower;
        EditText editTextTemperatureUpper;

        Double soilLower;
        Double soilUpper;
        Double humidityLower;
        Double humidityUpper;
        Double temperatureLower;
        Double temperatureUpper;


        errores = new ArrayList<String>();
        editTextSoilLower = (EditText) activity.findViewById(R.id.editTextSoilLower);
        editTextSoilUpper = (EditText) activity.findViewById(R.id.editTextSoilUpper);
        editTextHumidityLower = (EditText) activity.findViewById(R.id.editTextHumidityLower);
        editTextHumidityUpper = (EditText) activity.findViewById(R.id.editTextHumidityUpper);
        editTextTemperatureLower = (EditText) activity.findViewById(R.id.editTextTemperatureLower);
        editTextTemperatureUpper = (EditText) activity.findViewById(R.id.editTextTemperatureUpper);

        if(!editTextSoilLower.getText().toString().isEmpty()){
            soilLower = Double.parseDouble(editTextSoilLower.getText().toString());
        }else{
            soilLower=0.0;
        }

        if(!editTextSoilUpper.getText().toString().isEmpty()){
        soilUpper = Double.parseDouble(editTextSoilUpper.getText().toString());
        }else{
            soilUpper=0.0;
        }
        if(!editTextHumidityLower.getText().toString().isEmpty()){
        humidityLower = Double.parseDouble(editTextHumidityLower.getText().toString());
        }else{
            humidityLower=0.0;
        }

        if(!editTextHumidityUpper.getText().toString().isEmpty()){
        humidityUpper = Double.parseDouble(editTextHumidityUpper.getText().toString());
        }else{
            humidityUpper=0.0;
        }

        if(!editTextTemperatureLower.getText().toString().isEmpty()){
        temperatureLower = Double.parseDouble(editTextTemperatureLower.getText().toString());
        }else{
            temperatureLower=0.0;
        }

        if(!editTextTemperatureUpper.getText().toString().isEmpty()){
        temperatureUpper = Double.parseDouble(editTextTemperatureUpper.getText().toString());
        }else{
            temperatureUpper=0.0;
        }

        //Comprobamos las restricciones del dominio



        if(soilLower >= soilUpper){
                errores.add("Humedad suelo: El umbral inferior debe ser menor al superior");
            }
        if(humidityLower >= humidityUpper){
            errores.add("Humedad ambiente: El umbral inferior debe ser menor al superior");
        }
        if(temperatureLower >= temperatureUpper){
            errores.add("Temperatura ambiente: El umbral inferior debe ser menor al superior");
        }




        return errores;
    }

    public static void visualizaErrores(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }
}
