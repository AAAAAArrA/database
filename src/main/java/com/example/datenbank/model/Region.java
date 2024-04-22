package com.example.datenbank.model;

public class Region {
    private int id;
    private String name;

    public Region(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region(String name) {
        this.name = name;
    }

    // Getters and setters
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

    // For TableView display

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}