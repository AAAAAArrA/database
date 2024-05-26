package com.example.datenbank.controller;


import com.example.datenbank.model.*;
import com.example.datenbank.service.*;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class EreignisController implements Initializable {

    @FXML
    public DatePicker datePicker;

    @FXML
    public ComboBox<UnwetterArt> unwetterComboBox;

    @FXML
    public ComboBox<Region> regionComboBox;

    @FXML
    public ComboBox<Schaden> schadenComboBox;
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
    public TableView<Ereignis> tableView;

    @FXML
    public TableColumn<Ereignis, Integer> collId;

    @FXML
    public TableColumn<Ereignis, Date> datum;

    @FXML
    public TableColumn<Ereignis, String> unwetter;

    @FXML
    public TableColumn<Ereignis, String> region;

    @FXML
    public TableColumn<Ereignis, Integer> hoehe;

    @FXML
    public TableColumn<Ereignis, String> beschreibung;

    private Ereignis ereignis;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureAccess(LoginService.getRoleInSystem());
        UnwetterArtCRUD unwetterArtCRUD = new UnwetterArtCRUD();
        List<UnwetterArt> unwetterArts = unwetterArtCRUD.getUnwetterArtList();

        RegionCRUD regionCRUD = new RegionCRUD();
        List<Region> regions = regionCRUD.getRegionList();

        SchadenCRUD schadenCRUD = new SchadenCRUD();
        List<Schaden> schadens = schadenCRUD.getSchadenList();

        unwetterComboBox.setItems(FXCollections.observableList(unwetterArts));
        regionComboBox.setItems(FXCollections.observableList(regions));
        schadenComboBox.setItems(FXCollections.observableList(schadens));

        unwetterComboBox.setCellFactory(comboBox -> new ListCell<UnwetterArt>(){
            @Override
            protected void updateItem(UnwetterArt item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? " " : item.getBezeichnung());
            }
        });

        regionComboBox.setCellFactory(comboBox -> new ListCell<Region>(){
            @Override
            protected void updateItem(Region item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? " " : item.getName());
            }
        });

        schadenComboBox.setCellFactory(comboBox -> new ListCell<Schaden>(){
            @Override
            protected void updateItem(Schaden item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? " " : item.getBeschreibung());
            }
        });

        showEreignis();
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
    private void addEreignis(){
        UnwetterArt selectedUnwetterArt = unwetterComboBox.getSelectionModel().getSelectedItem();
        Region selectedRegion = regionComboBox.getSelectionModel().getSelectedItem();
        Schaden selectedSchaden = schadenComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = datePicker.getValue();

        Ereignis ereignis = new Ereignis();
        ereignis.setDatum(Date.valueOf(selectedDate));
        ereignis.setUnwetter(selectedUnwetterArt);
        ereignis.setRegionName(selectedRegion);
        ereignis.setSchaden(selectedSchaden);
        EreignisCRUD ereignisCRUD = new EreignisCRUD();
        ereignisCRUD.addEreignis(ereignis);
        showEreignis();

    }
    private void showEreignis(){
        EreignisCRUD handler = new EreignisCRUD();
        ObservableList<Ereignis> list = handler.getEreignisList();
        collId.setCellValueFactory(new PropertyValueFactory<Ereignis, Integer>("id"));
        unwetter.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            UnwetterArt unwetter = ereignis.getUnwetter();
            return new ReadOnlyStringWrapper(unwetter != null ? unwetter.getBezeichnung() : "");
        });
        region.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Region region = ereignis.getRegionName();
            return new ReadOnlyStringWrapper(region != null ? region.getName() : "");
        });
        hoehe.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Schaden schaden = ereignis.getSchaden();
            Integer hoeheValue = schaden != null ? schaden.getHoehe() : null;
            return new ReadOnlyObjectWrapper<>(hoeheValue);
        });
        beschreibung.setCellValueFactory(cellData -> {
            Ereignis ereignis = cellData.getValue();
            Schaden schaden = ereignis.getSchaden();

            return new ReadOnlyStringWrapper(schaden != null ? schaden.getBeschreibung() : "");
        });
        datum.setCellValueFactory(new PropertyValueFactory<Ereignis, Date>("datum"));
        tableView.setItems(list);
    }

    @FXML
    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent){
        try{
            Ereignis ereignis = tableView.getSelectionModel().getSelectedItem();

            UnwetterArtCRUD unwetterArtCRUD = new UnwetterArtCRUD();
            UnwetterArt unwetterArt = unwetterArtCRUD.getUnwetter(ereignis.getUnwetter().getBezeichnung());

            RegionCRUD regionCRUD = new RegionCRUD();
            Region region = regionCRUD.getRegion(ereignis.getRegionName().getName());

            SchadenCRUD schadenCRUD = new SchadenCRUD();
            Schaden schaden = schadenCRUD.getSchaden(ereignis.getSchaden().getBeschreibung(), ereignis.getSchaden().getHoehe());


            ereignis = new Ereignis(ereignis.getId(), unwetterArt, region,
                    schaden, ereignis.getDatum());
            this.ereignis = ereignis;
            datePicker.setValue(ereignis.getDatum().toLocalDate());
            unwetterComboBox.setValue(ereignis.getUnwetter());
            regionComboBox.setValue(ereignis.getRegionName());
            schadenComboBox.setValue(ereignis.getSchaden());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @FXML
    public void updateEreignis() {
        LocalDate selectedDate = datePicker.getValue();
        String details = "Unwetter: " + unwetterComboBox.getValue() +
                ", Region: " + regionComboBox.getValue() +
                ", Schaden: " + schadenComboBox.getValue() +
                ", Datum: " + selectedDate;

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this Ereignis with the following details?\n" + details,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    EreignisCRUD ereignisCRUD = new EreignisCRUD();
                    Ereignis ereignis = new Ereignis(this.ereignis.getId(), unwetterComboBox.getValue(),
                            regionComboBox.getValue(), schadenComboBox.getValue(), Date.valueOf(selectedDate));
                    ereignisCRUD.updateEreignis(ereignis);
                    showEreignis(); // Обновление отображения данных.
                    clearFields(); // Очистка полей формы.
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Update error", "Failed to update Ereignis: " + e.getMessage());
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
    public void deleteEreignis() {
        String details = "Unwetter: " + this.ereignis.getUnwetter() +
                ", Region: " + this.ereignis.getRegionName() +
                ", Schaden: " + this.ereignis.getSchaden() +
                ", Datum: " + this.ereignis.getDatum();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this Ereignis with the following details?\n" + details,
                ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    EreignisCRUD handler = new EreignisCRUD();
                    Ereignis ereignis = new Ereignis(this.ereignis.getId(), this.ereignis.getUnwetter(), this.ereignis.getRegionName(),
                            this.ereignis.getSchaden(), this.ereignis.getDatum());
                    handler.delete(ereignis);
                    showEreignis(); // Обновление отображения данных.
                    clearFields(); // Очистка полей формы.
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Delete error", "Failed to delete Ereignis: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    private  void clearFields(){
        datePicker.setValue(null);
        unwetterComboBox.setValue(null);
        regionComboBox.setValue(null);
        schadenComboBox.setValue(null);
    }

    @FXML
    private void clickNew(){
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
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
