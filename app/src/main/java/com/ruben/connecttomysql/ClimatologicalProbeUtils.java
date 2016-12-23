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


        soilLower = Double.parseDouble(editTextSoilLower.getText().toString());
        soilUpper = Double.parseDouble(editTextSoilUpper.getText().toString());
        humidityLower = Double.parseDouble(editTextHumidityLower.getText().toString());
        humidityUpper = Double.parseDouble(editTextHumidityUpper.getText().toString());
        temperatureLower = Double.parseDouble(editTextTemperatureLower.getText().toString());
        temperatureUpper = Double.parseDouble(editTextTemperatureUpper.getText().toString());

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
