package org.example.util;

import java.sql.*;

public class DatabaseUtils {
    private static DatabaseUtils instance;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;


    private DatabaseUtils() {
        databaseUrl = System.getenv("DB_URL");
        databaseUsername = System.getenv("DB_USERNAME");
        databasePassword = System.getenv("DB_PASSWORD");
        setDatabaseDriver();
        try {
            Connection testConn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            testConn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection during initialization", e);
        }
    }

    public static synchronized DatabaseUtils getInstance() {
        if (instance == null) {
            instance = new DatabaseUtils();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        setDatabaseDriver();
        try {
            conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    private void setDatabaseDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}