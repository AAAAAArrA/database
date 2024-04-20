package com.example.datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con;

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=unwetter;user=sa;password=12345;encrypt=false";

    public void getDBConnection(){
        synchronized (""){
            try{
                if(this.getCon() == null || this.getCon().isClosed()){
                    try{
                        setCon(DriverManager.getConnection(URL));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        DBConnection.con = con;
    }

    public static void closeConnection(){
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
