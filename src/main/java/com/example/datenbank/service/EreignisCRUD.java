package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.controller.EreignisController;
import com.example.datenbank.model.Einsatz;
import com.example.datenbank.model.Ereignis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EreignisCRUD {

    private DBConnection conn = new DBConnection();

    public ObservableList<Ereignis> getEreignisList(){
        ObservableList<Ereignis> list = FXCollections.observableArrayList();
        String query = "SELECT Ereignis.Ereignis_ID, Ereignis.Datum, Region.Name as Region, Unwetterart.Bezeichnung as Unwetter, Schaden.Hoehe as SchadenHoehe, Schaden.Beschreibung as SchadenBeschreibung \n" +
                "FROM Ereignis\n" +
                "JOIN Region  ON Ereignis.Region_ID = Region.Region_ID\n" +
                "JOIN UnwetterArt  ON Ereignis.UnwetterArt_ID = UnwetterArt.UnwetterArt_ID\n" +
                "JOIN Schaden  ON Ereignis.Schaden_ID = Schaden.Schaden_ID";
        try{
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Ereignis ereignis;
            while (rs.next()){
                ereignis = new Ereignis(
                        rs.getInt("Ereignis_ID"),
                        rs.getString("Unwetter"),
                        rs.getString("Region"),
                        rs.getInt("SchadenHoehe"),
                        rs.getString("SchadenBeschreibung"),
                        rs.getDate("Datum")
                );
                list.add(ereignis);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
