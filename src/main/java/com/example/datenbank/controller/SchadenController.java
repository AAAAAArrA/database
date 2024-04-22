package com.example.datenbank.controller;

import com.example.datenbank.model.Schaden;
import com.example.datenbank.service.SchadenCRUD;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        showSchaden();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
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
        BigDecimal hoehe = new BigDecimal(tfHoehe.getText());
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
        try {
            // Convert the text field input for hoehe from String to BigDecimal
            BigDecimal hoehe = new BigDecimal(tfHoehe.getText());
            String beschreibung = tfBeschreibung.getText();
            int schadenID = schaden.getSchadenID();  // Assuming selectedSchaden is an already selected instance of Schaden

            // Create a new Schaden object with the correct types for parameters
            Schaden schaden = new Schaden(schadenID, hoehe, beschreibung);

            SchadenCRUD handler = new SchadenCRUD();
            handler.updateSchaden(schaden);
            showSchaden();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (NumberFormatException e) {
            // Handle the case where the input string cannot be converted to BigDecimal
            System.out.println("Input format error: " + e.getMessage());
            // Implement more comprehensive error handling depending on your application requirements
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSchaden() {
        try {
            SchadenCRUD handler = new SchadenCRUD();
            handler.deleteSchaden(schaden);
            showSchaden();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
