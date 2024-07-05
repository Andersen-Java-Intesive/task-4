package org.example.service;

import org.example.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    private UserService userService;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private MockedStatic<DatabaseService> mockedDatabaseService;

    @BeforeEach
    public void setUp() throws Exception {
        AutoCloseable ac = MockitoAnnotations.openMocks(this);
        try (ac) {
            userService = new UserService();
            mockedDatabaseService = Mockito.mockStatic(DatabaseService.class);
            DatabaseService mockDatabaseService = mock(DatabaseService.class);
            mockedDatabaseService.when(DatabaseService::getInstance).thenReturn(mockDatabaseService);
            when(mockDatabaseService.getConnection(anyInt())).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        }
    }

    @AfterEach
    public void tearDown() {
        mockedDatabaseService.close();
    }


    @Test
    public void testCreate() throws SQLException {
        User user = new User();
        user.setId(2);
        user.setFirstName("Arthur");
        user.setSecondName("Auskern");
        user.setAge(33);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        boolean result = userService.create(user);

        verify(mockConnection).setAutoCommit(false);
        verify(mockPreparedStatement).setString(1, "Arthur");
        verify(mockPreparedStatement).setString(2, "Auskern");
        verify(mockPreparedStatement).setInt(3, 33);
        verify(mockPreparedStatement).executeUpdate();
        verify(mockConnection).commit();
        assertTrue(result);
    }

    @Test
    public void testFindById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("first_name")).thenReturn("Arnold");
        when(mockResultSet.getString("second_name")).thenReturn("Schwarzenegger");
        when(mockResultSet.getInt("age")).thenReturn(76);

        User user = userService.findById(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("Arnold", user.getFirstName());
        assertEquals("Schwarzenegger", user.getSecondName());
        assertEquals(76, user.getAge());
    }

    @Test
    public void testDeleteById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        userService.deleteById(1);
        verify(mockConnection).setAutoCommit(false);
        verify(mockStatement).execute("DELETE FROM user_info where id =1");
        verify(mockConnection).commit();
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setFirstName("Sylvester");
        user.setSecondName("Stallone");
        user.setAge(77);

        when(mockResultSet.next()).thenReturn(true);

        userService.update(user);

        verify(mockConnection).setAutoCommit(false);
        verify(mockPreparedStatement).setString(1, "Sylvester");
        verify(mockPreparedStatement).setString(2, "Stallone");
        verify(mockPreparedStatement).setInt(3, 77);

        verify(mockPreparedStatement).executeUpdate();
        verify(mockConnection).commit();
    }

    @Test
    public void testAll() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("first_name")).thenReturn("Arnold");
        when(mockResultSet.getString("second_name")).thenReturn("Schwarzenegger");
        when(mockResultSet.getInt("age")).thenReturn(76);

        LinkedHashSet<User> users = userService.all();

        assertNotNull(users);
        assertEquals(1, users.size());
        User user = users.iterator().next();
        assertEquals(1, user.getId());
        assertEquals("Arnold", user.getFirstName());
        assertEquals("Schwarzenegger", user.getSecondName());
        assertEquals(76, user.getAge());
    }
}
