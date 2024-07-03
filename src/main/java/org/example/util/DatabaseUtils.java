package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtils {
    private static DatabaseUtils instance;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;

    private static final Logger logger = LogManager.getLogger(DatabaseUtils.class);

    private DatabaseUtils() {
        loadProperties();
        setDatabaseDriver();
        try {
            Connection testConn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            testConn.close();
        } catch (SQLException e) {
            logger.error(e);
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
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException("Failed to load database properties", e);
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
            logger.error(e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void setDatabaseDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
