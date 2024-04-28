package com.example.datenbank.service;

import com.example.datenbank.DBConnection;
import com.example.datenbank.model.Organisation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrganisationCRUD {
    private DBConnection conn = new DBConnection();
    public void addOrganisation(Organisation organisation){
        String sql = "insert into Organisation(Name) values(?)";
        try {
            conn.getDBConnection();

            PreparedStatement st = conn.getCon().prepareStatement(sql);
            st.setString(1,organisation.getName());
            st.execute();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Organisation> getOrganisationList(){
        ObservableList<Organisation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM Organisation";
        try{
            conn.getDBConnection();

            PreparedStatement stmt = conn.getCon().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Organisation organisation;
            while (rs.next()){
                 organisation = new Organisation(rs.getInt("Organisation_ID"), rs.getString("Name"));
                 list.add(organisation);

            }
            rs.close();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void updateOrganisation(Organisation organisation){
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("UPDATE [Organisation]\n" +
                    "   SET [Name] = ?\n" +
                    " WHERE [Organisation_ID] = ?");

            stmt.setString(1, organisation.getName());
            stmt.setInt(2, organisation.getId());
            stmt.execute();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Organisation organisation){
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("DELETE FROM [Organisation] WHERE [Organisation_ID] = ?");

            stmt.setInt(1, organisation.getId());
            stmt.execute();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Organisation getOrganisationById(int id){
        String query = "SELECT Organisation_ID, Name FROM Organisation WHERE Organisation_ID = ?";

        Organisation organisation = new Organisation();
        try{
            conn.getDBConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                organisation.setId(rs.getInt("Organisation_ID"));
                organisation.setName(rs.getString("Name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return organisation;
    }

}

