package com.example.datenbank.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReaderHomeController {

    @FXML
    private Button btnEinsatz;

    @FXML
    private Button btnEreignis;

    @FXML
    private Button btnOrganisation;

    @FXML
    private Button btnRegion;

    @FXML
    private Button btnSchaden;

    @FXML
    private Button btnUnwetterart;


    @FXML
    private void handleEinsatz() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/einsatzr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ensatz Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEreignis() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/ereignisr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ereignis Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleOrganisation() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/organisationr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Organisation Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegion() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/regionr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Region Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSchaden() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/schadenr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Schaden Interface");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleUnwetterart() throws IOException {
        Stage stage  = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/unwetterartr.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Unwetterart Interface");
        stage.setScene(scene);
        stage.show();
    }
}

