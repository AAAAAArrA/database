package com.example.datenbank.model;

public class UnwetterArt {
    private int id;
    private String bezeichnung;

    public UnwetterArt(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public UnwetterArt(){

    }

    public UnwetterArt(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String toString() {
        return "UnwetterArt{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
