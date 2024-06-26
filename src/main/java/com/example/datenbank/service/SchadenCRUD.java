package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.model.Schaden;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SchadenCRUD {

    private DBConnection connection = new DBConnection();

    public void addSchaden(Schaden schaden) {
        String sql = "INSERT INTO Schaden (Hoehe, Beschreibung) VALUES (?, ?)";
        try {
            connection.getDBConnection();
            PreparedStatement st = connection.getCon().prepareStatement(sql);
            st.setInt(1, schaden.getHoehe());
            st.setString(2, schaden.getBeschreibung());
            st.execute();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Schaden> getSchadenList() {
        ObservableList<Schaden> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM Schaden";
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Schaden schaden = new Schaden(rs.getInt("Schaden_ID"), rs.getInt("Hoehe"), rs.getString("Beschreibung"));
                list.add(schaden);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateSchaden(Schaden schaden) {
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement(
                    "UPDATE Schaden SET Hoehe = ?, Beschreibung = ? WHERE Schaden_ID = ?");
            stmt.setInt(1, schaden.getHoehe());
            stmt.setString(2, schaden.getBeschreibung());
            stmt.setInt(3, schaden.getSchadenID());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSchaden(Schaden schaden) {
        String sql = "DELETE FROM Schaden WHERE Schaden_ID = ?";
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement(sql);
            stmt.setInt(1, schaden.getSchadenID());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Schaden getSchaden(String description, int quantity) {
        String query = "SELECT Schaden_ID, Beschreibung, Hoehe FROM Schaden WHERE Hoehe = ? AND CAST(Beschreibung AS NVARCHAR(MAX)) = ?";
        Schaden schaden = new Schaden();
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setString(2, description);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                schaden.setSchadenID(rs.getInt("Schaden_ID"));
                schaden.setHoehe(rs.getInt("Hoehe"));
                schaden.setBeschreibung(rs.getString("Beschreibung"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schaden;
    }
}
