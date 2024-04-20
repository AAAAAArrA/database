package com.example.datenbank;

import java.sql.Date;

public class Einsatz {
    private int id;

    private String name;

    private Organisation organisation;

    private Date beginn;

    private Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
