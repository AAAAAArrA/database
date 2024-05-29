package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.model.Einsatz;
import com.example.datenbank.model.Ereignis;
import com.example.datenbank.model.Organisation;
import com.example.datenbank.model.Region;
import com.example.datenbank.model.Schaden;
import com.example.datenbank.model.UnwetterArt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EinsatzCRUD {
    private DBConnection conn = new DBConnection();

    public ObservableList<Einsatz> getEinsatzList() {
        ObservableList<Einsatz> list = FXCollections.observableArrayList();
        String query = "SELECT \n" +
                "    e.Einsatz_ID,\n" +
                "    e.Beginn,\n" +
                "    e.Ende,\n" +
                "    e.Organisation_ID,\n" +
                "    org.Name AS Organisation_Name," +
                "    er.Ereignis_ID,\n" +
                "    er.Datum AS Ereignis_Datum,\n" +
                "    uw.Unwetterart_ID,\n" +
                "    uw.Bezeichnung AS Unwetterart_Bezeichnung,\n" +
                "    r.Region_ID,\n" +
                "    r.Name AS Region_Name,\n" +
                "    s.Schaden_ID,\n" +
                "    s.Hoehe AS Schaden_Hoehe,\n" +
                "    s.Beschreibung AS Schaden_Beschreibung\n" +
                "FROM einsatz e\n" +
                "JOIN ereignis er ON e.Ereignis_ID = er.Ereignis_ID\n" +
                "JOIN unwetterart uw ON er.Unwetterart_ID = uw.Unwetterart_ID\n" +
                "JOIN region r ON er.Region_ID = r.Region_ID\n" +
                "JOIN schaden s ON er.Schaden_ID = s.Schaden_ID\n" +
                "JOIN organisation org ON e.Organisation_ID = org.Organisation_ID";
        try {
            conn.getDBConnection();
            PreparedStatement statement = conn.getCon().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Einsatz einsatz = new Einsatz();
                UnwetterArt unwetter = new UnwetterArt();
                unwetter.setId(rs.getInt("Unwetterart_ID"));
                unwetter.setBezeichnung(rs.getString("Unwetterart_Bezeichnung"));

                Region region = new Region();
                region.setId(rs.getInt("Region_ID"));
                region.setName(rs.getString("Region_Name"));

                Schaden schaden = new Schaden();
                schaden.setSchadenID(rs.getInt("Schaden_ID"));
                schaden.setHoehe(rs.getInt("Schaden_Hoehe"));
                schaden.setBeschreibung(rs.getString("Schaden_Beschreibung"));

                Ereignis ereignis = new Ereignis();
                ereignis.setId(rs.getInt("Ereignis_ID"));
                ereignis.setDatum(rs.getDate("Ereignis_Datum"));
                ereignis.setUnwetter(unwetter);
                ereignis.setRegionName(region);
                ereignis.setSchaden(schaden);

                Organisation organisation = new Organisation();
                organisation.setId(rs.getInt("Organisation_ID"));
                organisation.setName(rs.getString("Organisation_Name"));

                einsatz.setId(rs.getInt("Einsatz_ID"));
                einsatz.setBeginn(rs.getDate("Beginn"));
                einsatz.setEnd(rs.getDate("Ende"));
                einsatz.setEreignis(ereignis);
                einsatz.setOrganisation(organisation);

                list.add(einsatz);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addEinsatz(Einsatz einsatz) {
        String query = "INSERT INTO einsatz (Ereignis_ID, Organisation_ID, Beginn, Ende) VALUES (?, ?, ?, ?)";
        try {
            conn.getDBConnection();
            PreparedStatement pstmt = conn.getCon().prepareStatement(query);
            pstmt.setInt(1, einsatz.getEreignis().getId());
            pstmt.setInt(2, einsatz.getOrganisation().getId());
            pstmt.setDate(3, einsatz.getBeginn());
            pstmt.setDate(4, einsatz.getEnd());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEinsatz(Einsatz einsatz) {
        try {
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("DELETE FROM Einsatz WHERE Einsatz_ID = ?");
            stmt.setInt(1, einsatz.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEinsatz(Einsatz einsatz) {
        String query = "UPDATE Einsatz SET Beginn = ?, Ende = ?, Ereignis_ID = ?, Organisation_ID = ? WHERE Einsatz_ID = ?";
        try {
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement(query);
            stmt.setDate(1, einsatz.getBeginn());
            stmt.setDate(2, einsatz.getEnd());
            stmt.setInt(3, einsatz.getEreignis().getId());
            stmt.setInt(4, einsatz.getOrganisation().getId());
            stmt.setInt(5, einsatz.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
