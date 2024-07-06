package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;

import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {
    private UserService userServiceImpl; // Экземпляр класса UserService, который будет тестироваться.

    @Mock
    private Connection mockConnection; // Mock объект для соединения с базой данных.

    @Mock
    private PreparedStatement mockPreparedStatement; // Mock объект для подготовленного запроса.

    @Mock
    private Statement mockStatement; // Mock объект для SQL запроса.

    @Mock
    private ResultSet mockResultSet; // Mock объект для результата запроса.

    private MockedStatic<DatabaseService> mockedDatabaseService; // Mock объект для статического метода DatabaseService.

    @BeforeEach
    public void setUp() throws Exception {
        AutoCloseable ac = MockitoAnnotations.openMocks(this); // Открытие моков.
        try (ac) {
            userServiceImpl = UserServiceImpl.getInstance(); // Инициализация UserService.
            mockedDatabaseService = Mockito.mockStatic(DatabaseService.class); // Мокирование статического метода.
            DatabaseService mockDatabaseService = mock(DatabaseService.class); // Создание мока DatabaseService.
            mockedDatabaseService.when(DatabaseService::getInstance).thenReturn(mockDatabaseService); // Возвращение мока при вызове getInstance.
            when(mockDatabaseService.getConnection(anyInt())).thenReturn(mockConnection); // Возвращение мока соединения при вызове getConnection.
            when(mockConnection.createStatement()).thenReturn(mockStatement); // Возвращение мока запроса при создании Statement.
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement); // Возвращение мока подготовленного запроса.
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet); // Возвращение мока результата запроса.
        }
    }

    @AfterEach
    public void tearDown() {
        mockedDatabaseService.close(); // Закрытие мока статического метода.
    }

    @Test
    public void testCreate() throws SQLException {
        UserDto userDto = new UserDto(); // Создание нового пользователя.
        userDto.setId(2);
        userDto.setFirstName("Arthur");
        userDto.setSecondName("Auskern");
        userDto.setAge(33);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement); // Возвращение мока подготовленного запроса.

        boolean result = userServiceImpl.add(userDto); // Вызов метода create.

        verify(mockConnection).setAutoCommit(false); // Проверка, что setAutoCommit был вызван с параметром false.
        verify(mockPreparedStatement).setString(1, "Arthur"); // Проверка, что setString был вызван с нужными параметрами.
        verify(mockPreparedStatement).setString(2, "Auskern");
        verify(mockPreparedStatement).setInt(3, 33);
        verify(mockPreparedStatement).executeUpdate(); // Проверка, что executeUpdate был вызван.
        verify(mockConnection).commit(); // Проверка, что commit был вызван.
        assertTrue(result); // Проверка, что результат метода create был true.
    }

    @Test
    public void testGetById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true); // Возвращение true при первом вызове next.
        when(mockResultSet.getInt("id")).thenReturn(1); // Возвращение значения 1 при вызове getInt.
        when(mockResultSet.getString("first_name")).thenReturn("Arnold"); // Возвращение строки "Arnold" при вызове getString.
        when(mockResultSet.getString("second_name")).thenReturn("Schwarzenegger");
        when(mockResultSet.getInt("age")).thenReturn(76);

        User user = userServiceImpl.find(1); // Вызов метода findById.

        assertNotNull(user); // Проверка, что пользователь не null.
        assertEquals(1, user.getId()); // Проверка, что id пользователя равен 1.
        assertEquals("Arnold", user.getFirstName()); // Проверка, что firstName пользователя равен "Arnold".
        assertEquals("Schwarzenegger", user.getSecondName());
        assertEquals(76, user.getAge());
    }

    @Test
    public void testDeleteById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true); // Возвращение true при вызове next.
        userServiceImpl.remove(1); // Вызов метода deleteById.
        verify(mockConnection).setAutoCommit(false); // Проверка, что setAutoCommit был вызван с параметром false.
        verify(mockStatement).execute("DELETE FROM user_info where id =1"); // Проверка, что execute был вызван с нужным SQL запросом.
        verify(mockConnection).commit(); // Проверка, что commit был вызван.
    }

    @Test
    public void testUpdate() throws SQLException {
        UserDto userDto = new UserDto(); // Создание нового пользователя.
        userDto.setId(1);
        userDto.setFirstName("Sylvester");
        userDto.setSecondName("Stallone");
        userDto.setAge(77);

        when(mockResultSet.next()).thenReturn(true); // Возвращение true при вызове next.

        userServiceImpl.edit(userDto); // Вызов метода update.

        verify(mockConnection).setAutoCommit(false); // Проверка, что setAutoCommit был вызван с параметром false.
        verify(mockPreparedStatement).setString(1, "Sylvester"); // Проверка, что setString был вызван с нужными параметрами.
        verify(mockPreparedStatement).setString(2, "Stallone");
        verify(mockPreparedStatement).setInt(3, 77);
        verify(mockPreparedStatement).executeUpdate(); // Проверка, что executeUpdate был вызван.
        verify(mockConnection).commit(); // Проверка, что commit был вызван.
    }

    @Test
    public void testGetAll() throws SQLException {
        when(mockResultSet.next())
                .thenReturn(true) // Возвращение true при первом вызове next.
                .thenReturn(true) // Возвращение true при втором вызове next.
                .thenReturn(false); // Возвращение false при третьем вызове next.
        when(mockResultSet.getInt("id"))
                .thenReturn(1) // Возвращение значения 1 при первом вызове getInt.
                .thenReturn(2); // Возвращение значения 2 при втором вызове getInt.
        when(mockResultSet.getString("first_name"))
                .thenReturn("Arnold") // Возвращение строки "Arnold" при первом вызове getString.
                .thenReturn("Sylvester"); // Возвращение строки "Sylvester" при втором вызове getString.
        when(mockResultSet.getString("second_name"))
                .thenReturn("Schwarzenegger") // Возвращение строки "Schwarzenegger" при первом вызове getString.
                .thenReturn("Stallone"); // Возвращение строки "Stallone" при втором вызове getString.
        when(mockResultSet.getInt("age"))
                .thenReturn(76) // Возвращение значения 76 при первом вызове getInt.
                .thenReturn(77); // Возвращение значения 77 при втором вызове getInt.

        LinkedHashSet<User> users = userServiceImpl.findAll(); // Вызов метода all.

        assertNotNull(users); // Проверка, что множество пользователей не null.
        assertEquals(2, users.size()); // Проверка, что размер множества пользователей равен 2.

        Iterator<User> iterator = users.iterator(); // Получение итератора для множества пользователей.

        User user1 = iterator.next(); // Получение первого пользователя.
        assertEquals(1, user1.getId()); // Проверка, что id первого пользователя равен 1.
        assertEquals("Arnold", user1.getFirstName()); // Проверка, что firstName первого пользователя равен "Arnold".
        assertEquals("Schwarzenegger", user1.getSecondName());
        assertEquals(76, user1.getAge());

        User user2 = iterator.next(); // Получение второго пользователя.
        assertEquals(2, user2.getId()); // Проверка, что id второго пользователя равен 2.
        assertEquals("Sylvester", user2.getFirstName()); // Проверка, что firstName второго пользователя равен "Sylvester".
        assertEquals("Stallone", user2.getSecondName());
        assertEquals(77, user2.getAge());
    }

}
