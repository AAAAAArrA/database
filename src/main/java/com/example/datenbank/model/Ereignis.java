package com.example.datenbank.model;

import java.util.Date;

public class Ereignis {
    private int id;

    private String unwetter;

    private String regionName;

    private int schaden;
    private String beschreibung;

    private Date datum;

    public Ereignis(int id, String unwetter, String regionName, int schaden, String beschreibung, Date datum) {
        this.id = id;
        this.unwetter = unwetter;
        this.regionName = regionName;
        this.schaden = schaden;
        this.beschreibung = beschreibung;
        this.datum = datum;
    }

    public Ereignis(String unwetter, String regionName, int schaden, String beschreibung, Date datum) {
        this.unwetter = unwetter;
        this.regionName = regionName;
        this.schaden = schaden;
        this.beschreibung = beschreibung;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnwetter() {
        return unwetter;
    }

    public void setUnwetter(String unwetter) {
        this.unwetter = unwetter;
    }

    public String getRegion() {
        return regionName;
    }

    public void setRegion(String regionName) {
        this.regionName = regionName;
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
