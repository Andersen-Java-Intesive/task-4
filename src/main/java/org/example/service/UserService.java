package org.example.service;

import org.example.model.User;
import org.example.repo.UserRepository;
import org.example.util.DatabaseUtils;

import java.sql.*;
import java.util.LinkedHashSet;

public class UserService implements UserRepository {
    @Override
    public boolean create(User user) {

        try (Connection connection = DatabaseUtils.getInstance().getConnection()) {
            String sql = "INSERT INTO user_info (first_name, second_name, age) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getSecondName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public User findById(int id) {
        Connection connection = DatabaseUtils.getInstance().getConnection();
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

    @Override
    public void deleteById(int id) {

        try (Connection connection = DatabaseUtils.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            if (findById(id) != null) {
                statement.execute("DELETE FROM user_info where id =" + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DatabaseUtils.getInstance().getConnection()) {
            String sql = "UPDATE user_info SET first_name = ?, second_name = ?, age = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getSecondName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setInt(4, user.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkedHashSet<User> all() {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        try (Connection connection = DatabaseUtils.getInstance().getConnection()) {
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
