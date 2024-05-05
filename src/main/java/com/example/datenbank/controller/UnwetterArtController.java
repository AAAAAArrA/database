package com.example.datenbank.controller;


import com.example.datenbank.model.UnwetterArt;
import com.example.datenbank.service.UnwetterArtCRUD;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UnwetterArtController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUnwetterArt();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }


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
    public TableView<UnwetterArt> tableView;

    @FXML
    public TableColumn<UnwetterArt, Integer> collId;

    @FXML
    public TableColumn<UnwetterArt, String> unwetterArtBezeichnung;

    private UnwetterArt unwetterArt;

    @FXML
    private void addUnwetterArt(){
        UnwetterArt unwetterArt = new UnwetterArt(bezeichnung.getText());
        UnwetterArtCRUD handler = new UnwetterArtCRUD();
        handler.addUnwetterArt(unwetterArt);
        showUnwetterArt();
    }

    @FXML
    private void showUnwetterArt(){
        UnwetterArtCRUD handler = new UnwetterArtCRUD();
        ObservableList<UnwetterArt> list = handler.getUnwetterArtList();
        collId.setCellValueFactory(new PropertyValueFactory<UnwetterArt, Integer>("id"));
        unwetterArtBezeichnung.setCellValueFactory(new PropertyValueFactory<UnwetterArt, String>("Bezeichnung"));
        tableView.setItems(list);
    }


    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try{
            UnwetterArt unwetterArt = tableView.getSelectionModel().getSelectedItem();
            unwetterArt = new UnwetterArt(unwetterArt.getId(), unwetterArt.getBezeichnung());
            this.unwetterArt = unwetterArt;

            bezeichnung.setText(unwetterArt.getBezeichnung());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

//    public void updateUnwetterArt(){
//        try{
//            UnwetterArtCRUD handler = new UnwetterArtCRUD();
//            UnwetterArt unwetterArt = new UnwetterArt(this.unwetterArt.getId(), bezeichnung.getText());
//            handler.updateUnwetterArt(unwetterArt);
//            showUnwetterArt();
//            clearFields();
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

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
                    showUnwetterArt(); // Обновление отображения данных.
                    clearFields(); // Очистка полей формы.
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
                UnwetterArt unwetterArt = new UnwetterArt(this.unwetterArt.getId(), currentBezeichnung);
                handler.delete(unwetterArt);
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
    private  void clearFields(){
        bezeichnung.setText("");
    }

    @FXML
    private void clickNew(){
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }
}
