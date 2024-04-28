package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.controller.EreignisController;
import com.example.datenbank.controller.RegionController;
import com.example.datenbank.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EreignisCRUD {

    private DBConnection conn = new DBConnection();

    public ObservableList<Ereignis> getEreignisList(){
        ObservableList<Ereignis> list = FXCollections.observableArrayList();
        String query = "SELECT e.Ereignis_ID, e.Datum, ua.Bezeichnung AS Unwetterart, r.Name AS Region, s.Hoehe, s.Beschreibung " +
                "FROM ereignis e " +
                "JOIN unwetterart ua ON e.Unwetterart_ID = ua.Unwetterart_ID " +
                "JOIN region r ON e.Region_ID = r.Region_ID " +
                "JOIN schaden s ON e.Schaden_ID = s.Schaden_ID";
        try{
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Ereignis ereignis;
            while (rs.next()){
                ereignis = new Ereignis();
                ereignis.setId(rs.getInt("Ereignis_ID"));
                ereignis.setDatum(rs.getDate("Datum"));

                UnwetterArt unwetterArt = new UnwetterArt();
                unwetterArt.setBezeichnung(rs.getString("Unwetterart"));
                ereignis.setUnwetter(unwetterArt);

                Region region = new Region();
                region.setName(rs.getString("Region"));
                ereignis.setRegionName(region);

                Schaden schaden = new Schaden();
                schaden.setHoehe(rs.getInt("Hoehe"));
                schaden.setBeschreibung(rs.getString("Beschreibung"));
                ereignis.setSchaden(schaden);
                list.add(ereignis);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void addEreignis(Ereignis ereignis){
        String sql = "INSERT INTO ereignis (Datum, Unwetterart_ID, Region_ID, Schaden_ID) VALUES (?, ?, ?, ?)";
        try{
            conn.getDBConnection();
            PreparedStatement pstmt = conn.getCon().prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(ereignis.getDatum().toLocalDate()));
            pstmt.setInt(2, ereignis.getUnwetter().getId());
            pstmt.setInt(3, ereignis.getRegionName().getId());
            pstmt.setInt(4, ereignis.getSchaden().getSchadenID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Ereignis ereignis){
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("DELETE FROM [Ereignis] WHERE [Ereignis_ID] = ?");

            stmt.setInt(1, ereignis.getId());
            stmt.execute();
            stmt.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateEreignis(Ereignis ereignis){
        try{
            String query = "UPDATE Ereignis\n" +
                    "   SET Datum = ?\n" +
                    "      ,Unwetterart_ID = ?\n" +
                    "      ,Region_ID = ?\n" +
                    "      ,Schaden_ID = ?\n" +
                    " WHERE Ereignis_ID = ?";
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            statement.setDate(1,ereignis.getDatum());
            statement.setInt(2, ereignis.getUnwetter().getId());
            statement.setInt(3, ereignis.getRegionName().getId());
            statement.setInt(4, ereignis.getSchaden().getSchadenID());
            statement.setInt(5, ereignis.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ereignis getEreignisByID(int id){
        String query = "SELECT e.Ereignis_ID, e.Datum, ua.Bezeichnung AS Unwetterart, r.Name AS Region, s.Hoehe, s.Beschreibung " +
                "FROM ereignis e " +
                "JOIN unwetterart ua ON e.Unwetterart_ID = ua.Unwetterart_ID " +
                "JOIN region r ON e.Region_ID = r.Region_ID " +
                "JOIN schaden s ON e.Schaden_ID = s.Schaden_ID " +
                "Where Ereignis_ID = ?";
        Ereignis ereignis = new Ereignis();
        try{
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                UnwetterArtCRUD unwetterArtCRUD = new UnwetterArtCRUD();
                UnwetterArt unwetterArt = unwetterArtCRUD.getUnwetter(rs.getString("Unwetterart"));

                RegionCRUD regionCRUD = new RegionCRUD();
                Region region = regionCRUD.getRegion(rs.getString("Region"));

                SchadenCRUD schadenCRUD = new SchadenCRUD();
                Schaden schaden = schadenCRUD.getSchaden(rs.getString("Beschreibung"), rs.getInt("Hoehe"));

                ereignis.setId(rs.getInt("Ereignis_ID"));
                ereignis.setDatum(rs.getDate("Datum"));
                ereignis.setUnwetter(unwetterArt);
                ereignis.setRegionName(region);
                ereignis.setSchaden(schaden);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ereignis;
    }

}
