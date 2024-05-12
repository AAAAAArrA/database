package com.example.datenbank.controller;

import com.example.datenbank.model.Schaden;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.SchadenCRUD;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class SchadenController implements Initializable {
    @FXML
    public TextField tfHoehe;
    @FXML
    public TextField tfBeschreibung;
    @FXML
    public Button btnNew;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnDelete;
    @FXML
    public TableView<Schaden> tableView;
    @FXML
    public TableColumn<Schaden, Integer> colSchadenID;
    @FXML
    public TableColumn<Schaden, BigDecimal> colHoehe;
    @FXML
    public TableColumn<Schaden, String> colBeschreibung;

    private Schaden schaden;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureAccess(LoginService.getRoleInSystem());
        showSchaden();
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


    private void showSchaden() {
        SchadenCRUD handler = new SchadenCRUD();
        ObservableList<Schaden> list = handler.getSchadenList();
        colSchadenID.setCellValueFactory(new PropertyValueFactory<Schaden, Integer>("schadenID"));
        colHoehe.setCellValueFactory(new PropertyValueFactory<Schaden,BigDecimal>("hoehe"));
        colBeschreibung.setCellValueFactory(new PropertyValueFactory<Schaden,String>("beschreibung"));
        tableView.setItems(list);
    }

    @FXML
    private void addSchaden() {
        Integer hoehe = Integer.parseInt(tfHoehe.getText());
        String beschreibung = tfBeschreibung.getText();
        Schaden schaden = new Schaden(hoehe, beschreibung);
        SchadenCRUD handler = new SchadenCRUD();
        handler.addSchaden(schaden);
        showSchaden();
    }

    @FXML
    private void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Schaden schaden = tableView.getSelectionModel().getSelectedItem();
            this.schaden = schaden;
            tfHoehe.setText(schaden.getHoehe().toString());
            tfBeschreibung.setText(schaden.getBeschreibung());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateSchaden() {
        if (schaden == null) {
            showAlert("Error", "No Schaden object selected.");
            return;
        }


        String beschreibung = schaden.getBeschreibung();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure, you want to update Schaden with the description: " + beschreibung + "?",
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Integer hoehe = Integer.parseInt(tfHoehe.getText());
                    String beschreibungg = tfBeschreibung.getText();
                    int schadenID = schaden.getSchadenID();

                    Schaden newSchaden = new Schaden(schadenID, hoehe, beschreibungg);

                    SchadenCRUD handler = new SchadenCRUD();
                    handler.updateSchaden(newSchaden);
                    showSchaden();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (NumberFormatException e) {
                    showAlert("Format error", "Please enter the correct value for Hoehe:" + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Upgrade error", "Schaden failed to upgrade: " + e.getMessage());
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



    public void deleteSchaden() {
        if (schaden == null) {
            showAlert("Error", "Schaden object not selected.");
            return;
        }


        String beschreibung = schaden.getBeschreibung();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure, you want to remove Schaden with the description: " + beschreibung + "?",
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    SchadenCRUD handler = new SchadenCRUD();
                    handler.deleteSchaden(schaden);
                    showSchaden();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Delete error", "Failed to uninstall Schaden: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void clickNew() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }

    private void clearFields() {
        tfHoehe.setText("");
        tfBeschreibung.setText("");
    }
}
