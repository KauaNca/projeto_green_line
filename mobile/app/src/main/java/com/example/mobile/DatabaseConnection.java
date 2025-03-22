package com.example.mobile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://crossover.proxy.rlwy.net:44484/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "KHOKgQYEysvzKWpxjZrLfgNEvFVrOBEQ";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
