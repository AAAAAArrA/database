package com.example.datenbank.controller;

import com.example.datenbank.model.Region;
import com.example.datenbank.service.RegionCRUD;
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

public class RegionController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRegion();
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
    public TableView<Region> tableView;

    @FXML
    public TableColumn<Region, Integer> collId;

    @FXML
    public TableColumn<Region, String> regionName;

    private Region region;

    @FXML
    private void addRegion(){
       Region region = new Region(name.getText());
        RegionCRUD handler = new RegionCRUD();
        handler.addRegion(region);
        showRegion();
    }

    @FXML
    private void showRegion(){
        RegionCRUD handler = new RegionCRUD();
        ObservableList<Region> list = handler.getRegionList();
        collId.setCellValueFactory(new PropertyValueFactory<Region, Integer>("id"));
        regionName.setCellValueFactory(new PropertyValueFactory<Region, String>("name"));
        tableView.setItems(list);
    }


//    @FXML
//    public void mouseClicked (MouseEvent e){
//        try{
//            Organisation organisation = tableView.getSelectionModel().getSelectedItem();
//            organisation = new Organisation(organisation.getId(), organisation.getName());
//            this.organisation = organisation;
//            name.setText(organisation.getName());
//            btnUpdate.setDisable(false);
//            btnDelete.setDisable(false);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }


    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try{
            Region region = tableView.getSelectionModel().getSelectedItem();
            region = new Region(region.getId(), region.getName());
            this.region= region;

            name.setText(region.getName());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateRegion(){
        try{
            RegionCRUD handler = new RegionCRUD();
            Region region = new Region(this.region.getId(), name.getText());
            handler.updateRegion(region);
            showRegion();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRegion(){
        try{
            RegionCRUD handler = new RegionCRUD();
            Region region = new Region(this.region.getId(), this.region.getName());
            handler.deleteRegion(region);
            showRegion();
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
