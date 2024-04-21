package com.example.datenbank.controller;

import com.example.datenbank.model.Region;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    private TableView<Region> tableView;

    @FXML
    private TableColumn<Region, Integer> idColumn;

    @FXML
    private TableColumn<Region, String> nameColumn;

//    @FXML
//    public void initialize() {
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        tableView.setItems(getRegionList());
//    }

//    private ObservableList<Region> getRegionList() {
//        List<Region> list = OrganisationCRUD.getRegions();
//        return FXCollections.observableArrayList(list);
//    }
}

