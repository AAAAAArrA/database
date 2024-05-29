package com.example.datenbank.controller;

import com.example.datenbank.model.Schaden;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.SchadenCRUD;
import com.example.datenbank.util.JAXBUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private Button backButton;
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
                setButtonVisibility(false, false, false, false);
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
        colSchadenID.setCellValueFactory(new PropertyValueFactory<>("schadenID"));
        colHoehe.setCellValueFactory(new PropertyValueFactory<>("hoehe"));
        colBeschreibung.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
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
                    showAlert("Format error", "Please enter the correct value for Hoehe: " + e.getMessage());
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
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/einsatz.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Einsatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEreignis() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/ereignis.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ereignis Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleOrganisation() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/organisation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Organisation Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegion() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/region.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Region Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSchaden() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/schaden.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Schaden Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleUnwetterart() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/unwetterart.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Unwetterart Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exportSchadenToXML() {
        try {
            SchadenCRUD schadenCRUD = new SchadenCRUD();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                FileWriter writer = new FileWriter(file);
                String xml = JAXBUtil.toXml(schadenCRUD.getSchadenList());
                writer.write(xml);
                writer.close();
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
