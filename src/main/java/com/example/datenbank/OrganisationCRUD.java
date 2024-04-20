package com.example.datenbank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrganisationCRUD {
//    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=unwetter;user=sa;password=12345;encrypt=false";
//
//    public static Connection getConnection() throws Exception {
//        return DriverManager.getConnection(URL);
//    }

    private DBConnection conn = new DBConnection();

//    public static List<Region> getRegions() {
//        List<Region> regions = new ArrayList<>();
//        String sql = "SELECT * FROM Region";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Region region = new Region();
//                region.setId(rs.getInt("Region_ID"));
//                region.setName(rs.getString("Name"));
//                regions.add(region);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return regions;
//    }

//    public static List<Organisation> getOrganisations(){
//        List<Organisation> organisations = new ArrayList<>();
//        String sql = "SELECT * FROM Organisation";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Organisation organisation = new Organisation();
//                organisation.setId(rs.getInt("Organisation_ID"));
//                organisation.setName(rs.getString("Name"));
//                organisations.add(organisation);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return organisations;
//    }

    public void addOrganisation(Organisation organisation){
        String sql = "insert into Organisation(Name) values(?)";
        try {
            conn.getDBConnection();
//            Connection conn = getConnection();
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
//            Connection conn = getConnection();
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
//            Connection conn = getConnection();
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
//            Connection conn = getConnection();
            PreparedStatement stmt = conn.getCon().prepareStatement("DELETE FROM [Organisation] WHERE [Organisation_ID] = ?");

            stmt.setInt(1, organisation.getId());
            stmt.execute();
            stmt.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

