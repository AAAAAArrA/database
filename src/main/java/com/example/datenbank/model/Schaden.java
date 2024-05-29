package com.example.datenbank.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Schaden")
@XmlType(propOrder = {"schadenID", "hoehe", "beschreibung"})
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

    public Schaden() {
    }

    @XmlElement
    public int getSchadenID() {
        return schadenID;
    }

    public void setSchadenID(int schadenID) {
        this.schadenID = schadenID;
    }

    @XmlElement
    public Integer getHoehe() {
        return hoehe;
    }

    public void setHoehe(Integer hoehe) {
        this.hoehe = hoehe;
    }

    @XmlElement
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
