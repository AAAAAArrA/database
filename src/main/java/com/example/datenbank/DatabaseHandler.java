package com.example.datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=unwetter;user=sa;password=12345;encrypt=false";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static List<Region> getRegions() {
        List<Region> regions = new ArrayList<>();
        String sql = "SELECT * FROM Region";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Region region = new Region();
                region.setId(rs.getInt("Region_ID"));
                region.setName(rs.getString("Name"));
                regions.add(region);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regions;
    }
}

