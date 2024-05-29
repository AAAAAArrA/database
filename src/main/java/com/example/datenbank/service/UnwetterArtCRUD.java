package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.model.UnwetterArt;
import com.example.datenbank.util.JAXBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UnwetterArtCRUD {
    private DBConnection conn = new DBConnection();

    public void addUnwetterArt(UnwetterArt unwetterArt){
        String sql = "insert into Unwetterart(Bezeichnung) values(?)";
        try {
            conn.getDBConnection();
            PreparedStatement st = conn.getCon().prepareStatement(sql);
            st.setString(1,unwetterArt.getBezeichnung());
            st.execute();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<UnwetterArt> getUnwetterArtList(){
        ObservableList<UnwetterArt> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM Unwetterart";
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            UnwetterArt unwetterArt;
            while (rs.next()){
                unwetterArt = new UnwetterArt(rs.getInt("Unwetterart_ID"), rs.getString("Bezeichnung"));
                list.add(unwetterArt);

            }
            rs.close();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void updateUnwetterArt(UnwetterArt unwetterArt){
        try{
            System.out.println(unwetterArt.getBezeichnung());
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("UPDATE [Unwetterart]\n" +
                    "   SET [Bezeichnung] = ?\n" +
                    " WHERE [Unwetterart_ID] = ?");

            stmt.setString(1, unwetterArt.getBezeichnung());
            stmt.setInt(2, unwetterArt.getId());
            stmt.execute();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(UnwetterArt unwetterArt){
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("DELETE FROM [Unwetterart] WHERE [Unwetterart_ID] = ?");

            stmt.setInt(1, unwetterArt.getId());
            stmt.execute();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public UnwetterArt getUnwetter(String name){
        String query = "SELECT Unwetterart_ID, Bezeichnung \n" +
                "FROM Unwetterart\n" +
                "WHERE CAST(Bezeichnung AS NVARCHAR(MAX)) = ?";
        UnwetterArt unwetterArt = new UnwetterArt();
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                unwetterArt.setId(rs.getInt("Unwetterart_ID"));
                unwetterArt.setBezeichnung(rs.getString("Bezeichnung"));

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return unwetterArt;
    }
    public void exportUnwetterArtToXML(File file) {
        ObservableList<UnwetterArt> unwetterArtList = getUnwetterArtList();
        try (FileWriter writer = new FileWriter(file)) {
            String xml = JAXBUtil.toXml(unwetterArtList);
            writer.write(xml);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}

