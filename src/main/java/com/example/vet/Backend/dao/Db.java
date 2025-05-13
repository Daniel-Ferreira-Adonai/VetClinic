package com.example.vet.Backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Db {
    private static Db instance;
    private Connection connection;

    private static final String url = "jdbc:mysql://localhost:3306/clinicaVeterinaria";
    private static final String user = "root";
    private static final String password = "";


    private Db() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Error to connect to Database: " + e.getMessage());
        }
    }

    public static Db getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new Db();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}
