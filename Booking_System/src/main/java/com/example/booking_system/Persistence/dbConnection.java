package com.example.booking_system.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbConnection {
    public static dbConnection instance;
    private Connection connection;
    private final String USERNAME = "sa";
    private final String PASSWORD = "1234";
    private final String DATABASE_NAME = "dbBooking_System";
    private final String PORT = "1433";
    private final String URL = "jdbc:sqlserver://localhost:" + PORT + ";databaseName=" + DATABASE_NAME;
    private final String ENCRYPT = "false";

    private dbConnection() {
        establishConnection();
    }

    public static dbConnection getInstance() {
        if (instance == null) {
            instance = new dbConnection();
        }
        return instance;
    }

    private Properties setupProps() {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("encrypt", ENCRYPT);
        return properties;
    }

    private void establishConnection() {
        try {
            connection = DriverManager.getConnection(URL, setupProps());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
