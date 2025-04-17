package com.example.datenbank;

import com.example.datenbank.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("База данных 'Unwetter'");

            LoginController controller = loader.getController();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


}

    public static void main(String[] args) {
        launch(args);
        DBConnection db = new DBConnection();
        db.getDBConnection();
    }
}