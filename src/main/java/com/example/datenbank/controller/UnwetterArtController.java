package com.example.datenbank.controller;

import com.example.datenbank.model.UnwetterArt;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.UnwetterArtCRUD;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnwetterArtController implements Initializable {
    @FXML
    private Button exportButton;

    @FXML
    public TextField bezeichnung;

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
    public TableView<UnwetterArt> tableView;

    @FXML
    public TableColumn<UnwetterArt, Integer> collId;

    @FXML
    public TableColumn<UnwetterArt, String> unwetterArtBezeichnung;

    private UnwetterArt unwetterArt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureAccess(LoginService.getRoleInSystem());
        showUnwetterArt();
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

    @FXML
    private void addUnwetterArt() {
        UnwetterArt unwetterArt = new UnwetterArt(bezeichnung.getText());
        UnwetterArtCRUD handler = new UnwetterArtCRUD();
        handler.addUnwetterArt(unwetterArt);
        showUnwetterArt();
    }

    @FXML
    private void showUnwetterArt() {
        UnwetterArtCRUD handler = new UnwetterArtCRUD();
        ObservableList<UnwetterArt> list = handler.getUnwetterArtList();
        collId.setCellValueFactory(new PropertyValueFactory<>("id"));
        unwetterArtBezeichnung.setCellValueFactory(new PropertyValueFactory<>("bezeichnung"));
        tableView.setItems(list);
    }

    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            UnwetterArt unwetterArt = tableView.getSelectionModel().getSelectedItem();
            unwetterArt = new UnwetterArt(unwetterArt.getId(), unwetterArt.getBezeichnung());
            this.unwetterArt = unwetterArt;

            bezeichnung.setText(unwetterArt.getBezeichnung());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateUnwetterArt() {
        String currentBezeichnung = bezeichnung.getText();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure, you want to update UnwetterArt with name: " + currentBezeichnung + "?",
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    UnwetterArtCRUD handler = new UnwetterArtCRUD();
                    UnwetterArt unwetterArt = new UnwetterArt(this.unwetterArt.getId(), currentBezeichnung);
                    handler.updateUnwetterArt(unwetterArt);
                    showUnwetterArt();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Upgrade error", "Failed to upgrade UnwetterArt: " + e.getMessage());
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

    public void deleteUnwetterArt() {
        String currentBezeichnung = this.unwetterArt.getBezeichnung();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete UnwetterArt with the designation: " + currentBezeichnung + "?",
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    UnwetterArtCRUD handler = new UnwetterArtCRUD();
                    handler.delete(this.unwetterArt);
                    showUnwetterArt();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Delete error", "Failed to delete UnwetterArt: " + e.getMessage());
                }
            }
        });
    }

    private void clearFields() {
        bezeichnung.setText("");
    }

    @FXML
    private void clickNew() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }

    @FXML
    private void exportUnwetterArtToXML() {
        try {
            UnwetterArtCRUD unwetterArtCRUD = new UnwetterArtCRUD();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                unwetterArtCRUD.exportUnwetterArtToXML(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/adminhome.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/einsatz.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Einsatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEreignis() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/ereignis.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Ereignis Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleOrganisation() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/organisation.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Organisation Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegion() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/region.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Region Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSchaden() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/schaden.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Schaden Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleUnwetterart() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/datenbank/unwetterart.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Unwetterart Interface");
        stage.setScene(scene);
        stage.show();
    }
}
