package com.example.datenbank.service;
import com.example.datenbank.DBConnection;
import com.example.datenbank.model.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class RegionCRUD {

    private DBConnection connection = new DBConnection();

    public void addRegion(Region region){
        String sql = "insert into Region(Name) values(?)";
        try {
            connection.getDBConnection();
            PreparedStatement st = connection.getCon().prepareStatement(sql);
            st.setString(1, region.getName());
            st.execute();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Region> getRegionList() {
        ObservableList<Region> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM Region";
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Region region;
            while (rs.next()) {
                region = new Region(rs.getInt("Region_ID"), rs.getString("Name"));
                list.add(region);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateRegion(Region region) {
        try {
            connection.getDBConnection();
            PreparedStatement stmt = connection.getCon().prepareStatement("UPDATE [Region]\n" +
                    "SET [Name]=?\n" +
                    "WHERE [Region_ID]=?");

            stmt.setString(1, region.getName());
            stmt.setInt(2, region.getId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void deleteRegion(Region region) {
            try{
                connection.getDBConnection();
                PreparedStatement stmt = connection.getCon().prepareStatement("DELETE FROM [Region] WHERE [Region_ID] = ?");
                stmt.setInt(1,region.getId());
                stmt.execute();
                stmt.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public Region getRegion(String name){
            String query = "SELECT Region_ID, Name FROM Region " +
                    "WHERE CAST(Name AS NVARCHAR(MAX)) = ?";
            Region region = new Region();
            try{
                connection.getDBConnection();
                PreparedStatement stmt = connection.getCon().prepareStatement(query);
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    region.setId(rs.getInt("Region_ID"));
                    region.setName(rs.getString("Name"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return  region;
        }

    }



