package com.ruben.connecttomysql;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 20/12/2016.
 */

public class FarmUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre,latitud,longitud;
        String nombreStr;
        Double latitudD,longitudD;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText3);
        latitud = (EditText) activity.findViewById(R.id.editText4);
        longitud = (EditText) activity.findViewById(R.id.editText5);

        nombreStr = nombre.getText().toString();

        //Comprobamos las restricciones del dominio
        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        if(!latitud.getText().toString().isEmpty()){
            latitudD = Double.parseDouble(latitud.getText().toString());
            if(latitudD <-90.0 || latitudD >90.0 ){
                errores.add("La latitud debe estar entre -90.0 y 90.0");
            }

        }
        if(!longitud.getText().toString().isEmpty()){
           longitudD = Double.parseDouble(longitud.getText().toString());
            if(longitudD <-180.0 || longitudD >180.0 ){
                errores.add("La latitud debe estar entre -180.0 y 180.0");
            }

        }

        return errores;
    }

    public static void visualizaErrores(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }
}
