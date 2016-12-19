package com.ruben.connecttomysql;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class Farm implements Serializable{
    private Integer id;
    private String name;
    private Double longitude;
    private Double latitude;


    public Farm(Integer id,String name, Double longitude,Double latitude){
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

    public Double getLatitude() {

        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String toString(){
        String s= getName();
        return s;
    }
}
