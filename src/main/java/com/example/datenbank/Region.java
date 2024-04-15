package com.example.datenbank;

public class Region {
    private int id;
    private String name;

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
        return name;
    }
}
