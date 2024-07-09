package org.example.repository.impl;

import org.example.dto.UserDto;
import org.example.exception.DatabaseOperationException;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.mapper.impl.UserMapperImpl;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.UserRepository;
import org.example.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepository instance;
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private final UserMapper userMapper = UserMapperImpl.getInstance();

    private static final String INSERT_USERS_SQL = "INSERT INTO user_info (first_name, second_name, age, team) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, first_name, second_name, age, team FROM user_info WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id, first_name, second_name, age, team FROM user_info ORDER BY id ASC";
    private static final String SELECT_ALL_USERS_BY_TEAM = "SELECT id, first_name, second_name, age, team FROM user_info WHERE team = ? ORDER BY id ASC";
    private static final String UPDATE_USERS_SQL = "UPDATE user_info SET first_name = ?, second_name = ?, age = ?, team = ? WHERE id = ?;";
    private static final String DELETE_USERS_SQL = "DELETE FROM user_info WHERE id = ?;";

    private UserRepositoryImpl() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public boolean create(UserDto userDto) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_UNCOMMITTED)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setString(1, userDto.getFirstName());
                preparedStatement.setString(2, userDto.getSecondName());
                preparedStatement.setInt(3, userDto.getAge());
                preparedStatement.setString(4, userDto.getTeam());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing sql query while creating user: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while creating user: " + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public User getById(int id) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_COMMITTED)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return userMapper.mapResultSetToUser(resultSet);
                } else {
                    throw new UserNotFoundException("User with id " + id + " not found");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting user: " + e.getMessage(), e);
        }
    }

    @Override
    public LinkedHashSet<User> getAll() {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_COMMITTED)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(userMapper.mapResultSetToUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting all users: " + e.getMessage(), e);
        }
    }

    @Override
    public LinkedHashSet<User> getAllByTeam(Team team) {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_COMMITTED)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_BY_TEAM)) {
                preparedStatement.setString(1, team.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    users.add(userMapper.mapResultSetToUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting all users by team: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(UserDto userDto) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
                if (getById(userDto.getId()) != null) {
                    preparedStatement.setString(1, userDto.getFirstName());
                    preparedStatement.setString(2, userDto.getSecondName());
                    preparedStatement.setInt(3, userDto.getAge());
                    preparedStatement.setString(4, userDto.getTeam());
                    preparedStatement.setInt(5, userDto.getId());
                    preparedStatement.executeUpdate();
                } else {
                    throw new UserNotFoundException("User with id " + userDto.getId() + " not found");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing sql query while updating user: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while updating user: " + e.getMessage(), e);
        }

    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
                if (getById(id) != null) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                } else {
                    throw new UserNotFoundException("User with id " + id + " not found");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing sql query while deleting user: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while deleting user: " + e.getMessage(), e);
        }
    }

}
