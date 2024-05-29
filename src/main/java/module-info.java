module com.example.datenbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens com.example.datenbank to javafx.fxml;
    exports com.example.datenbank;
    exports com.example.datenbank.controller;
    opens com.example.datenbank.controller to javafx.fxml;
    exports com.example.datenbank.model;
    opens com.example.datenbank.model to javafx.fxml, javafx.base, java.xml.bind;
    exports com.example.datenbank.service;
    opens com.example.datenbank.service to javafx.fxml;
    // Open the util package to java.xml.bind for JAXB
    opens com.example.datenbank.util to java.xml.bind;
}
