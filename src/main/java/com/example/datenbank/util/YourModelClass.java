package com.example.datenbank.util;

import com.example.datenbank.model.Region;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class YourModelClass {
    private ObservableList<Region> regions;

    @XmlJavaTypeAdapter(ObservableListAdapter.class)
    public ObservableList<Region> getRegions() {
        return regions;
    }

    public void setRegions(ObservableList<Region> regions) {
        this.regions = regions;
    }
}

