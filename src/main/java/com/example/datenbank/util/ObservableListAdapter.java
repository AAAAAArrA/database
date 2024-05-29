package com.example.datenbank.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class ObservableListAdapter<T> extends XmlAdapter<List<T>, ObservableList<T>> {
    @Override
    public ObservableList<T> unmarshal(List<T> v) {
        return FXCollections.observableArrayList(v);
    }

    @Override
    public List<T> marshal(ObservableList<T> v) {
        return List.copyOf(v);
    }
}


