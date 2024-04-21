module com.example.datenbank {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.datenbank to javafx.fxml;
    exports com.example.datenbank;
    exports com.example.datenbank.controller;
    opens com.example.datenbank.controller to javafx.fxml;
    exports com.example.datenbank.model;
    opens com.example.datenbank.model to javafx.fxml;
    exports com.example.datenbank.service;
    opens com.example.datenbank.service to javafx.fxml;
}