package org.example.util;
import java.sql.*;
public class DBConnection {
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "nurdos";
    public static final String DB_URL = "jdbc:postgresql://192.168.64.14:5432/Users";

    private DBConnection() {

    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (conn != null) {
                System.out.println("Connection " + conn + " is closed");
                conn.close();
            }
            if (stmt != null) {
                System.out.println("Statement " + stmt + " is closed");
                stmt.close();
            }
            if (rs != null) {
                System.out.println("Result set " + rs + " is closed");
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}