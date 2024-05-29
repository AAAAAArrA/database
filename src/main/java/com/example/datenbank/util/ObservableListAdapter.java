package com.example.datenbank.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.example.datenbank.model.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ObservableListAdapter extends XmlAdapter<List<Region>, ObservableList<Region>> {
    @Override
    public ObservableList<Region> unmarshal(List<Region> v) {
        return FXCollections.observableArrayList(v);
    }

    @Override
    public List<Region> marshal(ObservableList<Region> v) {
        return List.copyOf(v);
    }
}

