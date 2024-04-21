package com.example.datenbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        try {
            // Загрузка FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource("einsatz.fxml"));
            Parent root = loader.load();

            // Установка сцены
            Scene scene = new Scene(root);

            // Конфигурация и показ основного окна
            stage.setTitle("База данных 'Unwetter'");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
//    }

}

    public static void main(String[] args) {

        launch();
        DBConnection db = new DBConnection();
        db.getDBConnection();
    }
}