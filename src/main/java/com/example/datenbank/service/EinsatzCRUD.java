package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.model.Einsatz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EinsatzCRUD {
    private DBConnection conn = new DBConnection();

    public ObservableList<Einsatz> getEinsatzList(){
        ObservableList<Einsatz> list = FXCollections.observableArrayList();
        String query = "SELECT Einsatz.Einsatz_ID, Einsatz.Beginn, Einsatz.Ende, Organisation.Name AS orgName " +
                "FROM Einsatz JOIN Organisation ON Einsatz.Organisation_ID = Organisation.Organisation_ID";
        try{
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Einsatz einsatz;
            while (rs.next()){
                einsatz = new Einsatz(
                        rs.getInt("Einsatz_ID"),
//                        rs.getInt("Organisation_ID"),
                        rs.getString("orgName"),
                        rs.getDate("Beginn"),
                        rs.getDate("Ende") );
                list.add(einsatz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  list;

    }

}
