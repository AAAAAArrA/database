package com.example.datenbank;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    public void updateUnwetterArt(){
        try{
            UnwetterArtCRUD handler = new UnwetterArtCRUD();
            UnwetterArt unwetterArt = new UnwetterArt(this.unwetterArt.getId(), bezeichnung.getText());
            handler.updateUnwetterArt(unwetterArt);
            showUnwetterArt();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteUnwetterArt(){
        try{
            UnwetterArtCRUD handler = new UnwetterArtCRUD();
            UnwetterArt unwetterArt = new UnwetterArt(this.unwetterArt.getId(), this.unwetterArt.getBezeichnung());
            handler.delete(unwetterArt);
            showUnwetterArt();
            clearFields();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
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
