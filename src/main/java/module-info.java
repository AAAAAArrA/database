module com.example.datenbank {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.datenbank to javafx.fxml;
    exports com.example.datenbank;
}