package com.example.datenbank.controller;

import com.example.datenbank.model.Organisation;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.OrganisationCRUD;
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

public class OrganisationController implements Initializable {
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
    public TableView<Organisation> tableView;

    @FXML
    public TableColumn<Organisation, Integer> collId;

    @FXML
    public TableColumn<Organisation, String> organisationName;

    private Organisation organisation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureAccess(LoginService.getRoleInSystem());
        showOrganisation();
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


public void updateOrganisation() {

    String currentName = name.getText();


    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to update the Organisation with the name: " + currentName + "?",
            ButtonType.YES, ButtonType.NO);


    confirmationAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            try {
                OrganisationCRUD handler = new OrganisationCRUD();
                Organisation organisation = new Organisation(this.organisation.getId(), currentName);
                handler.updateOrganisation(organisation);
                showOrganisation();
                clearFields();
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Update error", "Failed to update Organisation: " + e.getMessage());
            }
        }
    });
}
    public void deleteOrganisation() {

        String currentName = this.organisation.getName();


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete the Organisation with the name: " + currentName + "?",
                ButtonType.YES, ButtonType.NO);


        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    OrganisationCRUD handler = new OrganisationCRUD();
                    Organisation organisation = new Organisation(this.organisation.getId(), currentName);
                    handler.delete(organisation);
                    showOrganisation();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Delete error", "Failed to delete Organisation: " + e.getMessage());
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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

    @FXML
    private void handleEinsatz() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/einsatz.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEreignis() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/ereignis.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleOrganisation() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/organisation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegion() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/region.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSchaden() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/schaden.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleUnwetterart() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/unwetterart.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }




}
