package org.example;

import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static Connection connection;
    private static String jdbcURL = "jdbc:postgresql://192.168.64.14:5432/Users";
    private static String jdbcUsername = "postgres";
    private static String jdbcPassword = "nurdos";
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addUser(User user) {
        try {
            String query = "insert into user_info(first_name, second_name, age) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "select * from user_info order by second_name";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                int age = resultSet.getInt("age");
                users.add(new User(id, firstName, secondName, age));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private static void deleteUser(int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "delete from user_info where id = " + id;
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void editUser(int id, String firstName, String secondName, int age) {
        try {
            Statement statement = connection.createStatement();
            String query = "update user_info set first_name = '" + firstName + "', second_name = '" + secondName + "', age = " + age + " where id = " + id;
            statement.executeUpdate(query);
            System.out.println("Updated success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}