package com.example.datenbank.controller;

import com.example.datenbank.model.Organisation;
import com.example.datenbank.service.OrganisationCRUD;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrganisationController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showOrganisation();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }


    @FXML
    public TextField name;

    @FXML
    public Button btnNew;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnDelete;

    @FXML
    public TableView<Organisation> tableView;

    @FXML
    public TableColumn<Organisation, Integer> collId;

    @FXML
    public TableColumn<Organisation, String> organisationName;

    private Organisation organisation;

    @FXML
    private void addOrganisation(){
        Organisation organisation = new Organisation(name.getText());
        OrganisationCRUD handler = new OrganisationCRUD();
        handler.addOrganisation(organisation);
        showOrganisation();
    }

    @FXML
    private void showOrganisation(){
        OrganisationCRUD handler = new OrganisationCRUD();
        ObservableList<Organisation> list = handler.getOrganisationList();
        collId.setCellValueFactory(new PropertyValueFactory<Organisation, Integer>("id"));
        organisationName.setCellValueFactory(new PropertyValueFactory<Organisation, String>("name"));
        tableView.setItems(list);
    }

    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try{
            Organisation organisation = tableView.getSelectionModel().getSelectedItem();
            organisation = new Organisation(organisation.getId(), organisation.getName());
            this.organisation = organisation;

            name.setText(organisation.getName());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateOrganisation(){
        try{
            OrganisationCRUD handler = new OrganisationCRUD();
            Organisation organisation = new Organisation(this.organisation.getId(), name.getText());
            handler.updateOrganisation(organisation);
            showOrganisation();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteOrganisation(){
        try{
            OrganisationCRUD handler = new OrganisationCRUD();
            Organisation organisation = new Organisation(this.organisation.getId(), this.organisation.getName());
            handler.delete(organisation);
            showOrganisation();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void clearFields(){
        name.setText("");
    }

    @FXML
    private void clickNew(){
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }
}
