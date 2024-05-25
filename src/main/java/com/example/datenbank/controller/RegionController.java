package com.example.datenbank.controller;

import com.example.datenbank.model.Region;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.RegionCRUD;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegionController implements Initializable {
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
    private Button backButton;

    @FXML
    public TableView<Region> tableView;

    @FXML
    public TableColumn<Region, Integer> collId;

    @FXML
    public TableColumn<Region, String> regionName;

    private Region region;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureAccess(LoginService.getRoleInSystem());
        showRegion();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void configureAccess(String role) {
        // Установить видимость кнопок в зависимости от роли
        switch (role) {
            case "Admin":
                setButtonVisibility(true, true, true, true);
                break;
            case "Readwrite":
                setButtonVisibility(true, true, true, false);
                break;
            case "Reader":
                setButtonVisibility(false, false, false, false);
                break;
            default:
                setButtonVisibility(false, false, false, false); // Нет доступа
                break;
        }
    }

    private void setButtonVisibility(boolean newVisible, boolean saveVisible, boolean updateVisible, boolean deleteVisible) {
        btnNew.setVisible(newVisible);
        btnSave.setVisible(saveVisible);
        btnUpdate.setVisible(updateVisible);
        btnDelete.setVisible(deleteVisible);
    }


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

    public void updateRegion() {
        if (this.region == null) {
            showAlert("Error", "Region not selected.");
            return;
        }
        String regionName = this.region.getName();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure, you want to update the region " + regionName + "?",
                ButtonType.YES, ButtonType.NO);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    RegionCRUD handler = new RegionCRUD();
                    Region region = new Region(this.region.getId(), name.getText()); // Здесь мы используем TextField для обновления имени.
                    handler.updateRegion(region);
                    showRegion();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Update error", "Failed to update region: " + e.getMessage());
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void deleteRegion() {
        if (this.region == null) {
            showAlert("Error","Region not selected.");
            return;
        }

        String regionName = this.region.getName();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure, you want to remove the region " + regionName + "?",
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    RegionCRUD handler = new RegionCRUD();
                    handler.deleteRegion(this.region);
                    showRegion();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    showAlert("Delete error", "Failed to delete region: " + e.getMessage());
                }
            }
        });
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

    @FXML
    void handleBackButtonAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/adminhome.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
