package com.example.datenbank.controller;

import com.example.datenbank.model.Einsatz;
import com.example.datenbank.model.Organisation;
import com.example.datenbank.service.EinsatzCRUD;
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

public class EinsatzController implements Initializable {

    @FXML
    private ComboBox<Organisation> organisationComboBox;

    @FXML
    public Button btnNew;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnDelete;

    @FXML
    public TableView<Einsatz> tableView;

    public TableColumn<Einsatz, Integer> collId;

    @FXML
    public TableColumn<Einsatz, String> organisationName;


    @FXML
    public TableColumn<Einsatz, Date> beginn;

    @FXML
    public TableColumn<Einsatz, Date> ende;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEinsatz();
    }

    @FXML
    private void addEinsatz(){

    }

    @FXML
    private void showEinsatz(){
        EinsatzCRUD handler = new EinsatzCRUD();
        ObservableList<Einsatz> list = handler.getEinsatzList();
        collId.setCellValueFactory(new PropertyValueFactory<Einsatz, Integer>("id"));
        organisationName.setCellValueFactory(new PropertyValueFactory<Einsatz, String>("organisationName"));
        beginn.setCellValueFactory(new PropertyValueFactory<Einsatz, Date>("beginn"));
        ende.setCellValueFactory(new PropertyValueFactory<Einsatz, Date>("end"));
        tableView.setItems(list);


    }

    @FXML
    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent){

    }

    @FXML
    public void updateEinsatz(){

    }

    @FXML
    public void deleteEinsatz(){

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
