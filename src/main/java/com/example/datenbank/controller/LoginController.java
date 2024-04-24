package com.example.datenbank.controller;

import com.example.datenbank.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text actiontarget;

    @FXML protected void handleLogin() {
        LoginService loginService = new LoginService();
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        if (loginService.login(usernameField.getText(), passwordField.getText())) {
            actiontarget.setText("Login successful.");
        } else {
            actiontarget.setText("Login failed.");
        }
    }
}
