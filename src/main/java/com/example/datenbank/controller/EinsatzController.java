package com.example.datenbank.controller;

import com.example.datenbank.model.*;
import com.example.datenbank.service.EinsatzCRUD;
import com.example.datenbank.service.EreignisCRUD;
import com.example.datenbank.service.LoginService;
import com.example.datenbank.service.OrganisationCRUD;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EinsatzController implements Initializable {

    @FXML
    public ComboBox<Ereignis> ereignisComboBox;

    @FXML
    public ComboBox<Organisation> organisationComboBox;

    @FXML
    public DatePicker beginnDate;

    @FXML
    public DatePicker endDate;

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
    public TableView<Einsatz> tableView;

    @FXML
    public TableColumn<Einsatz, Integer> collId;

    @FXML
    public TableColumn<Einsatz, String> unwetter;

    @FXML
    public TableColumn<Einsatz, String> region;

    @FXML
    public TableColumn<Einsatz, Integer> schaden;

    @FXML
    public TableColumn<Einsatz, String> organisationName;


    @FXML
    public TableColumn<Einsatz, Date> beginn;

    @FXML
    public TableColumn<Einsatz, Date> ende;

    private Einsatz einsatz;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureAccess(LoginService.getRoleInSystem());
        EreignisCRUD ereignisCRUD = new EreignisCRUD();
        List<Ereignis> ereignisList = ereignisCRUD.getEreignisList();

        OrganisationCRUD organisationCRUD = new OrganisationCRUD();
        List<Organisation> organisations = organisationCRUD.getOrganisationList();

        ereignisComboBox.setItems(FXCollections.observableList(ereignisList));
        organisationComboBox.setItems(FXCollections.observableList(organisations));

        ereignisComboBox.setCellFactory(comboBox -> new ListCell<Ereignis>(){
            @Override
            protected void updateItem(Ereignis item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? " " : String.valueOf(item.getId()));
            }
        });

        organisationComboBox.setCellFactory(comboBox -> new ListCell<Organisation>(){
            @Override
            protected void updateItem(Organisation item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? " " : item.getName());
            }
        });

        showEinsatz();
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
    private void addEinsatz(){
        Ereignis ereignis = ereignisComboBox.getSelectionModel().getSelectedItem();
        Organisation organisation = organisationComboBox.getSelectionModel().getSelectedItem();
        LocalDate beginn = beginnDate.getValue();
        LocalDate end = endDate.getValue();

        Einsatz einsatz1 = new Einsatz();
        einsatz1.setEreignis(ereignis);
        einsatz1.setOrganisation(organisation);
        einsatz1.setBeginn(java.sql.Date.valueOf(beginn));
        einsatz1.setEnd(java.sql.Date.valueOf(end));

        EinsatzCRUD einsatzCRUD = new EinsatzCRUD();
        einsatzCRUD.addEinsatz(einsatz1);
        showEinsatz();
    }

    @FXML
    private void showEinsatz(){
        EinsatzCRUD handler = new EinsatzCRUD();
        ObservableList<Einsatz> list = handler.getEinsatzList();
        collId.setCellValueFactory(new PropertyValueFactory<Einsatz, Integer>("id"));
        unwetter.setCellValueFactory(cellData -> {
            Einsatz einsatz1 = cellData.getValue();
            UnwetterArt unwetter = einsatz1.getEreignis().getUnwetter();
            return new ReadOnlyStringWrapper(unwetter != null ? unwetter.getBezeichnung() : "");
        });
        region.setCellValueFactory(cellData -> {
            Einsatz einsatz1 = cellData.getValue();
            Region region1 = einsatz1.getEreignis().getRegionName();
            return new ReadOnlyStringWrapper(region1 != null ? region1.getName() : "");
        });
        schaden.setCellValueFactory(cellData ->{
            Einsatz einsatz1 = cellData.getValue();
            Schaden schaden1 = einsatz1.getEreignis().getSchaden();
            Integer hoeheValue = schaden1 != null ? schaden1.getHoehe() : null;
            return new ReadOnlyObjectWrapper<>(hoeheValue);
        });
        organisationName.setCellValueFactory(cellData -> {
            Einsatz einsatz1 = cellData.getValue();
            Organisation organisation = einsatz1.getOrganisation();
            return new ReadOnlyStringWrapper(organisation != null ? organisation.getName() : " ");
        });
        beginn.setCellValueFactory(new PropertyValueFactory<Einsatz, Date>("beginn"));
        ende.setCellValueFactory(new PropertyValueFactory<Einsatz, Date>("end"));
        tableView.setItems(list);
    }

    @FXML
    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent){
        try{
            Einsatz einsatz = tableView.getSelectionModel().getSelectedItem();

            EreignisCRUD ereignisCRUD = new EreignisCRUD();
            Ereignis ereignis = ereignisCRUD.getEreignisByID(einsatz.getEreignis().getId());

            OrganisationCRUD organisationCRUD = new OrganisationCRUD();
            Organisation organisation = organisationCRUD.getOrganisationById(einsatz.getOrganisation().getId());

            einsatz = new Einsatz(einsatz.getId(), ereignis, organisation, einsatz.getBeginn(), einsatz.getEnd());
            this.einsatz = einsatz;

            ereignisComboBox.setValue(einsatz.getEreignis());
            organisationComboBox.setValue(einsatz.getOrganisation());
            beginnDate.setValue(einsatz.getBeginn().toLocalDate());
            endDate.setValue(einsatz.getEnd().toLocalDate());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


@FXML
public void updateEinsatz(){
    LocalDate begin = beginnDate.getValue();
    LocalDate end = endDate.getValue();

    if (begin == null || end == null) {
        showAlert("Input Error", "Both start and end dates must be selected to update an Einsatz.");
        return;
    }



    String details = String.format("Are you sure you want to delete this Einsatz scheduled from %s to %s?",
            this.einsatz.getBeginn().toString(), this.einsatz.getEnd().toString());


    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, details, ButtonType.YES, ButtonType.NO);

    confirmationAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            try {
                EinsatzCRUD einsatzCRUD = new EinsatzCRUD();
                Einsatz einsatz = new Einsatz(this.einsatz.getId(), ereignisComboBox.getValue(),
                        organisationComboBox.getValue(), java.sql.Date.valueOf(begin), java.sql.Date.valueOf(end));
                einsatzCRUD.updateEinsatz(einsatz);
                showEinsatz();
                clearFields();
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Update Error", "Failed to update Einsatz: " + e.getMessage());
            }
        }
    });
}
    @FXML
    public void deleteEinsatz(){
        String details = String.format("Are you sure you want to delete this Einsatz scheduled from %s to %s?",
                this.einsatz.getBeginn().toString(), this.einsatz.getEnd().toString());

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, details, ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    EinsatzCRUD einsatzCRUD = new EinsatzCRUD();
                    Einsatz einsatz = new Einsatz(this.einsatz.getId(), this.einsatz.getEreignis(),
                            this.einsatz.getOrganisation(), this.einsatz.getBeginn(), this.einsatz.getEnd());
                    einsatzCRUD.deleteEinsatz(einsatz);
                    showEinsatz();
                    clearFields();
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Delete Error", "Failed to delete Einsatz: " + e.getMessage());
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


    @FXML
    private  void clearFields(){
        ereignisComboBox.setValue(null);
        organisationComboBox.setValue(null);
        beginnDate.setValue(null);
        endDate.setValue(null);
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
