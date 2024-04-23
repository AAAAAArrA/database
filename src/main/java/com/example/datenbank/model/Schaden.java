package com.example.datenbank.model;

import java.math.BigDecimal;

public class Schaden {
    private Integer schadenID;
    private Integer hoehe;
    private String beschreibung;


    public Schaden(int schadenID, Integer hoehe, String beschreibung) {
        this.schadenID = schadenID;
        this.hoehe = hoehe;
        this.beschreibung = beschreibung;
    }

    public Schaden(Integer hoehe, String beschreibung) {
        this.hoehe = hoehe;
        this.beschreibung = beschreibung;
    }

    public Schaden(){

    }

    public int getSchadenID() {
        return schadenID;
    }

    public void setSchadenID(int schadenID) {
        this.schadenID = schadenID;
    }

    public Integer getHoehe() {
        return hoehe;
    }

    public void setHoehe(Integer hoehe) {
        this.hoehe = hoehe;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return "Schaden{" +
                "schadenID=" + schadenID +
                ", hoehe=" + hoehe +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
