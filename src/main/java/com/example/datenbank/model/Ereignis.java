package com.example.datenbank.model;

import java.time.LocalDate;
import java.sql.Date;

public class Ereignis {

    private int id;

    private UnwetterArt unwetter;

    private Region regionName;

    private Schaden schaden;

    private Date datum;

    public Ereignis(int id, UnwetterArt unwetter, Region regionName, Schaden schaden, Date datum) {
        this.id = id;
        this.unwetter = unwetter;
        this.regionName = regionName;
        this.schaden = schaden;
        this.datum = datum;
    }

    public Ereignis(UnwetterArt unwetter, Region regionName, Schaden schaden, Date datum) {
        this.unwetter = unwetter;
        this.regionName = regionName;
        this.schaden = schaden;
        this.datum = datum;
    }
    public Ereignis(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UnwetterArt getUnwetter() {
        return unwetter;
    }

    public void setUnwetter(UnwetterArt unwetter) {
        this.unwetter = unwetter;
    }

    public Region getRegionName() {
        return regionName;
    }

    public void setRegionName(Region regionName) {
        this.regionName = regionName;
    }

    public Schaden getSchaden() {
        return schaden;
    }

    public void setSchaden(Schaden schaden) {
        this.schaden = schaden;
    }

    public Date getDatum() {
        return datum;
    }

    @Override
    public String toString() {
        return "Ereignis{" +
                "id=" + id +
                ", unwetter=" + unwetter +
                ", regionName=" + regionName +
                ", schaden=" + schaden +
                ", datum=" + datum +
                '}';
    }

    public void setDatum(Date datum) {
        this.datum = datum;

    }


}
