package org.example.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtils {
    private static DatabaseUtils instance;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;


    private DatabaseUtils() {
        loadProperties();
        setDatabaseDriver();
        try {
            Connection testConn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            testConn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection during initialization", e);
        }
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("secrets.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find secrets.properties");
            }
            Properties prop = new Properties();
            prop.load(input);
            databaseUrl = prop.getProperty("DB_URL");
            databaseUsername = prop.getProperty("DB_USERNAME");
            databasePassword = prop.getProperty("DB_PASSWORD");
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load database properties", ex);
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

    private void setDatabaseDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
