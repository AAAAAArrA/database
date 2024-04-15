package com.example.datenbank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Controller {

    @FXML
    private TableView<Region> tableView;

    @FXML
    private TableColumn<Region, Integer> idColumn;

    @FXML
    private TableColumn<Region, String> nameColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.setItems(getRegionList());
    }

    private ObservableList<Region> getRegionList() {
        List<Region> list = DatabaseHandler.getRegions();
        return FXCollections.observableArrayList(list);
    }
}

