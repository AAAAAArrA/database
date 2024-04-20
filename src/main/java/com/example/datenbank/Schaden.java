package com.example.datenbank;

import java.math.BigDecimal;

public class Schaden {
    private int schadenID;
    private BigDecimal hoehe;
    private String beschreibung;


    public Schaden(int schadenID, BigDecimal hoehe, String beschreibung) {
        this.schadenID = schadenID;
        this.hoehe = hoehe;
        this.beschreibung = beschreibung;
    }



    public int getSchadenID() {
        return schadenID;
    }

    public void setSchadenID(int schadenID) {
        this.schadenID = schadenID;
    }

    public BigDecimal getHoehe() {
        return hoehe;
    }

    public void setHoehe(BigDecimal hoehe) {
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
