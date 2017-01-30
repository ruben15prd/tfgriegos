package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class SeveralTimesDaySchedule extends Irrigation implements Serializable{
    private Date startDate;
    private Date endDate;

    public SeveralTimesDaySchedule(){
        super();
    }

    public SeveralTimesDaySchedule(Integer id, String name, Date cancelMoment, Date startDate, Date endDate){
        super();
        this.setId(id);
        this.setName(name);
        this.setCancelMoment(cancelMoment);
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String toString(){
        String s= getName();
        return s;
    }
}
