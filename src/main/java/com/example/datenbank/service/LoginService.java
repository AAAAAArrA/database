package com.example.datenbank.service;



import com.example.datenbank.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class LoginService {
    private DBConnection conn = new DBConnection();

    public boolean login(String username, String password) {
        String sql = "SELECT PasswordHash, Role FROM Users WHERE Username = ?";
        conn.getDBConnection();
        try (PreparedStatement pstmt = conn.getCon().prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("PasswordHash");

                System.out.println(storedHash);
                System.out.println(hashPassword(password));
                if (storedHash.equals(hashPassword(password))) {

                    System.out.println("Login successful. Role: " + rs.getString("Role"));
                    openRoleBasedInterface(rs.getString("Role"));
                    return true;
                }
            }
            System.out.println("Login failed.");
            return false;
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    private void openRoleBasedInterface(String role) throws IOException {
        try {
            Stage stage = new Stage(); // Создание новой сцены
            Parent root;
            switch (role) {
            case "Reader":
                root = FXMLLoader.load(getClass().getResource("organisation.fxml")); // Загрузка FXML для админа
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Interface");
                break;
            case "ReadWriter":
                root = FXMLLoader.load(getClass().getResource("region.fxml")); // Загрузка FXML для админа
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Interface");
                break;
            case "Admin":
                root = FXMLLoader.load(getClass().getResource("einsatz.fxml")); // Загрузка FXML для админа
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Interface");
                break;
            default:
                System.out.println("Unknown role");
                return;
        }
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error loading the view for role: " + role);
    }
    }
}
