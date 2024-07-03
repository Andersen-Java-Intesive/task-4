package org.example.util;
import java.sql.*;
public class DBUtils {
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "root";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/Users";

    private DBUtils() {

    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
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