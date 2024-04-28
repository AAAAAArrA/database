package com.example.datenbank.model;

import java.sql.Date;

public class Einsatz {
    private int id;

    private Ereignis ereignis;

    private Organisation organisation;

    private Date beginn;

    private Date end;

    public Einsatz(int id, Ereignis ereignis, Organisation organisation, Date beginn, Date end) {
        this.id = id;
        this.ereignis = ereignis;
        this.organisation = organisation;
        this.beginn = beginn;
        this.end = end;
    }

    public Einsatz(Ereignis ereignis, Organisation organisation, Date beginn, Date end) {
        this.ereignis = ereignis;
        this.organisation = organisation;
        this.beginn = beginn;
        this.end = end;
    }

    public Einsatz (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ereignis getEreignis() {
        return ereignis;
    }

    public void setEreignis(Ereignis ereignis) {
        this.ereignis = ereignis;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Date getBeginn() {
        return beginn;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
