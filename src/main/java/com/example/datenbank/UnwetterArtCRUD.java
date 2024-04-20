package com.example.datenbank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

}

