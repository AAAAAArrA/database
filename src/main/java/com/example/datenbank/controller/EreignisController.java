package com.example.datenbank.controller;


import com.example.datenbank.model.*;
import com.example.datenbank.service.EreignisCRUD;
import com.example.datenbank.service.RegionCRUD;
import com.example.datenbank.service.SchadenCRUD;
import com.example.datenbank.service.UnwetterArtCRUD;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
}
