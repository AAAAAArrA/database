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
import java.util.Objects;

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

            switch (role) {
            case "Reader":
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/datenbank/readerhome.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Reader Interface");
                stage.setScene(scene);
                break;
            case "Readwrite":
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/datenbank/readwriterhome.fxml"));
                Parent root2 = loader2.load();
                Scene scene2 = new Scene(root2);
                stage.setTitle("Read-writer Interface");
                stage.setScene(scene2);
                break;
            case "Admin":
                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/example/datenbank/adminhome.fxml"));
                Parent root3 = loader3.load();
                Scene scene3 = new Scene(root3);
                stage.setTitle("Admin Interface");
                stage.setScene(scene3);
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
