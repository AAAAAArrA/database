package com.example.datenbank.controller;


import com.example.datenbank.model.*;
import com.example.datenbank.service.EreignisCRUD;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EreignisController implements Initializable {

    @FXML
    public ComboBox<UnwetterArt> unwetterComboBox;

    @FXML
    public ComboBox<Region> regionComboBox;

    @FXML
    public ComboBox<Schaden> schadenComboBox;
    @FXML
    public Button btnNew;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnDelete;

    @FXML
    public TableView<Ereignis> tableView;

    @FXML
    public TableColumn<Ereignis, Integer> collId;

    @FXML
    public TableColumn<Ereignis, Date> datum;

    @FXML
    public TableColumn<Ereignis, String> unwetter;

    @FXML
    public TableColumn<Ereignis, String> region;

    @FXML
    public TableColumn<Ereignis, Integer> hoehe;

    @FXML
    public TableColumn<Ereignis, String> beschreibung;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEreignis();
    }

    @FXML
    private void addEreignis(){

    }
    private void showEreignis(){
        EreignisCRUD handler = new EreignisCRUD();
        ObservableList<Ereignis> list = handler.getEreignisList();
        collId.setCellValueFactory(new PropertyValueFactory<Ereignis, Integer>("id"));
        unwetter.setCellValueFactory(new PropertyValueFactory<Ereignis, String>("unwetter"));
        region.setCellValueFactory(new PropertyValueFactory<Ereignis, String>("regionName"));
        hoehe.setCellValueFactory(new PropertyValueFactory<Ereignis, Integer>("schaden"));
        beschreibung.setCellValueFactory(new PropertyValueFactory<Ereignis, String>("beschreibung"));
        datum.setCellValueFactory(new PropertyValueFactory<Ereignis, Date>("datum"));
        tableView.setItems(list);
    }

    @FXML
    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent){

    }

    @FXML
    public void updateEreignis(){

    }

    @FXML
    public void deleteEreignis(){

    }

    @FXML
    private  void clearFields(){

    }

    @FXML
    private void clickNew(){
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }
}
