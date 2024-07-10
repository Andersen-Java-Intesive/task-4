package org.example.repository.impl;

import org.example.dto.MarkDto;
import org.example.exception.DatabaseOperationException;
import org.example.exception.MarkNotFoundException;
import org.example.mapper.MarkMapper;
import org.example.mapper.impl.MarkMapperImpl;
import org.example.model.Mark;
import org.example.repository.MarkRepository;
import org.example.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.UUID;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Types.OTHER;

public class MarkRepositoryImpl implements MarkRepository {
    private static MarkRepository instance;
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private final MarkMapper markMapper = MarkMapperImpl.getInstance();
    private static final String INSERT_MARKS_SQL = "INSERT INTO marks (lesson_date, user_one_id, user_one_mark, user_two_id, user_two_mark) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_MARK_BY_ID = "SELECT id, lesson_date, user_one_id, user_one_mark, user_two_id, user_two_mark FROM marks WHERE id = ?";
    private static final String SELECT_ALL_MARKS = "SELECT id, lesson_date, user_one_id, user_one_mark, user_two_id, user_two_mark FROM marks ORDER BY id ASC";
    private static final String UPDATE_MARKS_SQL = "UPDATE marks SET lesson_date = ?, user_one_id = ?, user_one_mark = ?, user_two_id = ?, user_two_mark = ? WHERE id = ?";
    private static final String DELETE_MARKS_SQL = "DELETE FROM marks WHERE id = ?";

    private MarkRepositoryImpl() {
    }

    public static synchronized MarkRepository getInstance() {
        if (instance == null) {
            instance = new MarkRepositoryImpl();
        }
        return instance;
    }

    @Override
    public boolean create(MarkDto markDto) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_UNCOMMITTED)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MARKS_SQL)) {
                preparedStatement.setDate(1, markDto.getLessonDate());
                preparedStatement.setObject(2, markDto.getUserOneId(), OTHER);
                preparedStatement.setDouble(3, markDto.getUserOneMark());
                preparedStatement.setObject(4, markDto.getUserTwoId(), OTHER);
                preparedStatement.setDouble(5, markDto.getUserTwoMark());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing SQL query while creating mark: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while creating mark: " + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public Mark getById(UUID id) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_COMMITTED)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MARK_BY_ID)) {
                preparedStatement.setString(1, id.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return markMapper.mapResultSetToMark(resultSet);
                } else {
                    throw new MarkNotFoundException("Mark with id " + id + " not found");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting mark: " + e.getMessage(), e);
        }
    }

    @Override
    public LinkedHashSet<Mark> getAll() {
        LinkedHashSet<Mark> marks = new LinkedHashSet<>();
        try (Connection connection = databaseService.getConnection(TRANSACTION_READ_COMMITTED)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_MARKS);
            while (resultSet.next()) {
                marks.add(markMapper.mapResultSetToMark(resultSet));
            }
            return marks;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting all marks: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(MarkDto markDto) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MARKS_SQL)) {
                if (getById(markDto.getId()) != null) {
                    preparedStatement.setDate(1, markDto.getLessonDate());
                    preparedStatement.setObject(2, markDto.getUserOneId(), OTHER);
                    preparedStatement.setDouble(3, markDto.getUserOneMark());
                    preparedStatement.setObject(4, markDto.getUserTwoId(), OTHER);
                    preparedStatement.setDouble(5, markDto.getUserTwoMark());
                    preparedStatement.setObject(6, markDto.getId(), OTHER);
                    preparedStatement.executeUpdate();
                } else {
                    throw new MarkNotFoundException("Mark with id " + markDto.getId() + " not found");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing SQL query while updating mark: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while updating mark: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        try (Connection connection = databaseService.getConnection(TRANSACTION_REPEATABLE_READ)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MARKS_SQL)) {
                preparedStatement.setObject(1, id, OTHER);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseOperationException("Error executing SQL query while deleting mark: " + e.getMessage(), e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error committing transaction while deleting mark: " + e.getMessage(), e);
        }
    }
}
