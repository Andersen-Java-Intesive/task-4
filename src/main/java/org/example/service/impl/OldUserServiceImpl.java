package org.example.service.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.DatabaseService;

import java.sql.*;
import java.util.LinkedHashSet;

import static java.sql.Connection.*;

public class OldUserServiceImpl {

    public static OldUserServiceImpl getInstance() {
        return new OldUserServiceImpl();
    }

    public boolean create(User user) {

        try (Connection connection = DatabaseService.getInstance().getConnection(TRANSACTION_READ_UNCOMMITTED)) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO user_info (first_name, second_name, age) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getSecondName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public User findById(int id) {
        Connection connection = DatabaseService.getInstance().getConnection(TRANSACTION_READ_COMMITTED);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info where id =" + id);
            if (resultSet.next()) {
                return createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void deleteById(int id) {

        try (Connection connection = DatabaseService.getInstance().getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            try {
                if (findById(id) != null) {
                    statement.execute("DELETE FROM user_info where id =" + id);
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(User user) {
        try (Connection connection = DatabaseService.getInstance().getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            String sql = "UPDATE user_info SET first_name = ?, second_name = ?, age = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (findById(user.getId()) == null) {
                    throw new RuntimeException("User with if " + user.getId() + " not found");
                }
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getSecondName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setInt(4, user.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashSet<User> all() {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        try (Connection connection = DatabaseService.getInstance().getConnection(TRANSACTION_READ_COMMITTED)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info");
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setAge(resultSet.getInt("age"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        return user;
    }
}


