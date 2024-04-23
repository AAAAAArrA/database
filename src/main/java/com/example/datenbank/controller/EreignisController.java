package com.example.datenbank.controller;


import com.example.datenbank.model.*;
import com.example.datenbank.service.EreignisCRUD;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
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
        unwetter.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            UnwetterArt unwetter = ereignis.getUnwetter();
            return new ReadOnlyStringWrapper(unwetter != null ? unwetter.getBezeichnung() : "");
        });
        region.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Region region = ereignis.getRegionName();
            return new ReadOnlyStringWrapper(region != null ? region.getName() : "");
        });
        hoehe.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Schaden schaden = ereignis.getSchaden();
            Integer hoeheValue = schaden != null ? schaden.getHoehe() : null;
            return new ReadOnlyObjectWrapper<>(hoeheValue);
        });
        beschreibung.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Schaden schaden = ereignis.getSchaden();

            return new ReadOnlyStringWrapper(schaden != null ? schaden.getBeschreibung() : "");
        });
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
