package com.equipo.superttapp.projects.model;

import com.equipo.superttapp.util.DateFormater;

import java.util.Date;

public class ProyectoModel {
    private Integer id;
    private String name;
    private Date date;
    private String textDate;
    private Double rate;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getTextDate() {
        return textDate;
    }

    public void setTextDate(String textDate) {
        this.textDate = textDate;
    }

    public void updateTextDate() {
        setTextDate(DateFormater.convertDateToString(this.date));
    }

    public void updateDate() {
        setDate(DateFormater.convertStringToDate(this.textDate));
    }
}
