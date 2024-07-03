package org.example.dao.impl;

import org.example.dao.abs.UserDAO;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean create(User type) {

        try (Connection connection = DBUtils.getConnection();) {
            Statement statement = connection.createStatement();

            // TODO вставить валидные поля в строку. В идеале - сделать через prepared statement.
            String sql = null;
//            = "INSERT INTO `employees` (`id`, `name`, `last_name`, `age`, `office_id`, `passport_id`, " +
//                    "`updated_ts`, `created_ts`) VALUES ('" + employee.getId() + "', '" + employee.getName() + "', '" +
//                    employee.getLastName() + "', '" + employee.getAge() + "', '" + employee.getOffice().getId() + "', '" +
//                    employee.getPassport().getId() + "', '" + employee.getUpdatedTs() + "', " + employee.getCreatedTs() + ")";
            int count = statement.executeUpdate(sql);
            return count == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(int key) {
        Connection connection = DBUtils.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users where id =" + key);
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
                statement.execute("DELETE FROM users where id =" + id);
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User type) {

        try (Connection connection = DBUtils.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute ("SELECT * FROM users");
            //TODO сделать валидный запрос
//                    ("UPDATE employees SET name = '" + employee.getName() + "', last_name = '" + employee.getLastName() +
//                    "', age = '" + employee.getAge() + "', office_id = '" + employee.getOffice().getId() + "', passport_id = '" +
//                    employee.getPassport().getId() + "' WHERE id = " + employee.getId());
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
            rs = statement.executeQuery("SELECT * FROM users");
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
