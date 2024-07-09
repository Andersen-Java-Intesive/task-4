package org.example.service;

import org.example.exception.DatabaseOperationException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class DatabaseService {

    private static DatabaseService instance;

    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;

    private DatabaseService() {
        loadProperties();
        setDatabaseDriver();
        try {
            Connection testConn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            testConn.close();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to establish database connection during initialization", e);
        }
    }

    public Connection getConnection(int isolationLevel) {
        Connection connection;
        setDatabaseDriver();
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            connection.setTransactionIsolation(setDatabaseUser(isolationLevel));
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get database connection", e);
        }
        return connection;
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private void setDatabaseDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int setDatabaseUser(int isolationLevel) {
        if (isolationLevel == TRANSACTION_READ_UNCOMMITTED) {
            return TRANSACTION_READ_UNCOMMITTED;
        }
        if (isolationLevel == TRANSACTION_READ_COMMITTED) {
            return TRANSACTION_READ_COMMITTED;
        }
        if (isolationLevel == TRANSACTION_REPEATABLE_READ) {
            return TRANSACTION_REPEATABLE_READ;
        }
        if (isolationLevel == TRANSACTION_SERIALIZABLE) {
            return TRANSACTION_SERIALIZABLE;
        }
        return TRANSACTION_READ_COMMITTED;
    }

}
