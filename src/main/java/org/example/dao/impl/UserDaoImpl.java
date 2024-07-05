package org.example.dao.impl;

import org.example.dao.abs.UserDAO;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean create(User type) {

        try (Connection connection = DBUtils.getConnection();) {
            String sql = "INSERT INTO user_info (first_name, second_name, age) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, type.getFirstName());
                pstmt.setString(2, type.getSecondName());
                pstmt.setInt(3, type.getAge());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public User findById(int key) {
        Connection connection = DBUtils.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user_info where id =" + key);
            if (rs.next()) {
                return createUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {

        try (Connection connection = DBUtils.getConnection()) {
            Statement statement = connection.createStatement();
            if (findById(id) == null) {
                statement.execute("DELETE FROM user_info where id =" + id);
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User type) {

        try (Connection connection = DBUtils.getConnection()) {
            String sql = "UPDATE user_info SET first_name = ?, second_name = ?, age = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, type.getFirstName());
                pstmt.setString(2, type.getSecondName());
                pstmt.setInt(3, type.getAge());
                pstmt.setInt(4, type.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (findById(type.getId()).equals(type)) {
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<User> all() {
        Set<User> users = new HashSet<>();
        Connection connection = DBUtils.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM user_info");
            while (rs.next()) {
                users.add(createUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.release(connection, statement, rs);
        }
    }

    private User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setAge(rs.getInt("age"));
        user.setFirstName(rs.getString("first_name"));
        user.setSecondName(rs.getString("second_name"));
        return user;
    }
}
