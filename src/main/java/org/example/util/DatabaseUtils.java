package org.example.util;

import java.sql.*;

public class DatabaseUtils {
    private static DatabaseUtils instance;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;


    private DatabaseUtils() {
//        databaseUrl = System.getenv("DB_URL");
//        databaseUsername = System.getenv("DB_USERNAME");
//        databasePassword = System.getenv("DB_PASSWORD");
          databaseUrl = "jdbc:postgresql://192.168.64.14:5432/Users";
          databaseUsername = "postgres";
          databasePassword = "nurdos";
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
        Connection connection;
        setDatabaseDriver();
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void setDatabaseDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}