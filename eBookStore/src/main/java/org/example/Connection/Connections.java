package org.example.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    // Database URL, username, and password
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=YNDHOnlineBookStore;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa@123";
    
     public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(URL, USER, PASSWORD);
     }
}