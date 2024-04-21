package com.example.datenbank.model;

import java.sql.Date;

public class Einsatz {
    private int id;

    private int organisationId;

    private String organisationName;

    private Date beginn;

    private Date end;

    public Einsatz(int id, int organisationId, String organisationName, Date beginn, Date end) {
        this.id = id;
        this.organisationId = organisationId;
        this.organisationName = organisationName;
        this.beginn = beginn;
        this.end = end;
    }

    public Einsatz(int organisationId, String organisationName, Date beginn, Date end) {
        this.organisationId = organisationId;
        this.organisationName = organisationName;
        this.beginn = beginn;
        this.end = end;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
