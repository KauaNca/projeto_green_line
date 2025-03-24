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
            System.out.println("Tentando conectar ao banco de dados...");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
