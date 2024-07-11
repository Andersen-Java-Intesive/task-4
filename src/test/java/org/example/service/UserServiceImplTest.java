package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.MarkRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.impl.Pair;
import org.example.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.LinkedList;

import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(PowerMockRunner.class)
@PrepareForTest({UserServiceImpl.class, UserRepositoryImpl.class, DatabaseService.class, MarkRepository.class, UserRepository.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DatabaseService mockDatabaseService;

    @Mock
    private MarkRepository markRepository;

    private UserServiceImpl userService;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(UserRepository.class);
        PowerMockito.mockStatic(MarkRepository.class);
        PowerMockito.mockStatic(DatabaseService.class);

        Whitebox.setInternalState(UserRepositoryImpl.class, "databaseService", mockDatabaseService);
        Whitebox.setInternalState(DatabaseService.class, "instance", mockDatabaseService);

        Map<Pair<User, User>, Integer> mockPairHistory = new HashMap<>();
        userService = new UserServiceImpl(mockPairHistory);

        Whitebox.setInternalState(userService, "userRepository", userRepository);
        Whitebox.setInternalState(userService, "markRepository", markRepository);

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDatabaseService.getConnection(anyInt())).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(userRepository.create(any(UserDto.class))).thenReturn(true);

        when(DatabaseService.getInstance()).thenReturn(mockDatabaseService);
    }

    @Test
    public void testAdd() throws SQLException {
        UserServiceImpl userService = (UserServiceImpl) UserServiceImpl.getInstance();

        UserDto userDto = new UserDto();
        userDto.setFirstName("Nurdos");
        userDto.setSecondName("Ramazan");
        userDto.setAge(new Date(2000, 1, 1));
        userDto.setTeam("PINK_TEAM");

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        DatabaseService mockDatabaseService = mock(DatabaseService.class);
        when(mockDatabaseService.getConnection(anyInt())).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1); // Simulate successful execution

        Whitebox.setInternalState(UserRepositoryImpl.class, "databaseService", mockDatabaseService);

        boolean result = userService.add(userDto);

        assertTrue(result);
        verify(mockDatabaseService).getConnection(TRANSACTION_READ_UNCOMMITTED);
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).commit();
        verify(mockStatement).setString(1, "Nurdos");
        verify(mockStatement).setString(2, "Ramazan");
        verify(mockStatement).setDate(3, new Date(userDto.getAge().getTime())); // Use java.sql.Date for SQL Date
        verify(mockStatement).executeUpdate();
    }
    @Test
    public void testFind() {
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFirstName("Nurdos");
        mockUser.setSecondName("Ramazan");
        mockUser.setAge(new Date(2000, 1, 1));
        mockUser.setTeam("PINK_TEAM");

        when(userRepository.getById(userId)).thenReturn(mockUser);

        User foundUser = userService.find(userId);

        assertNotNull(foundUser);
        assertEquals("Nurdos", foundUser.getFirstName());
        assertEquals("Ramazan", foundUser.getSecondName());
        assertEquals(new Date(2000, 1, 1), foundUser.getAge());
        assertEquals("PINK_TEAM", foundUser.getTeam());
    }

    @Test
    public void testFindAll() {
        LinkedHashSet<User> mockUsers = new LinkedHashSet<>();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        User user1 = new User(userId1, "name1", "second1", new Date(2000, 1, 1), "ORANGE_TEAM");
        User user2 = new User(userId2, "name2", "second2", new Date(2000, 1, 1), "PINK_TEAM");
        mockUsers.add(user1);
        mockUsers.add(user2);

        when(userRepository.getAll()).thenReturn(mockUsers);

        LinkedHashSet<User> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());

        for (User user : foundUsers) {
            if (user.getId().equals(userId1)) {
                assertEquals("name1", user.getFirstName());
                assertEquals("second1", user.getSecondName());
                assertEquals(new Date(2000, 1, 1), user.getAge());
                assertEquals("ORANGE_TEAM", user.getTeam());
            } else if (user.getId().equals(userId2)) {
                assertEquals("name2", user.getFirstName());
                assertEquals("second2", user.getSecondName());
                assertEquals(new Date(2000, 1, 1), user.getAge());
                assertEquals("PINK_TEAM", user.getTeam());
            } else {
                fail("Unexpected user ID: " + user.getId());
            }
        }

        verify(userRepository).getAll();
    }

    @Test
    public void testEdit() {
        UUID userId = UUID.randomUUID();
        UserDto updatedUserDto = new UserDto(userId, "newName", "newSecond", new Date(2000, 1, 1), "ORANGE_TEAM");

        User existingUser = new User(userId, "exisingName", "existings", new Date(2000, 1, 1), "PINK_TEAM");
        when(userRepository.getById(userId)).thenReturn(existingUser);

        userService.edit(updatedUserDto);

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(userRepository).update(userDtoCaptor.capture());

        UserDto capturedUserDto = userDtoCaptor.getValue();
        assertEquals(updatedUserDto, capturedUserDto);

        User updatedUser = new User(userId, "newName", "newSecond", new Date(2000, 1, 1), "ORANGE_TEAM");
        when(userRepository.getById(userId)).thenReturn(updatedUser);

        User fetchedUser = userRepository.getById(userId);
        assertEquals(updatedUser.getFirstName(), fetchedUser.getFirstName());
        assertEquals(updatedUser.getSecondName(), fetchedUser.getSecondName());
        assertEquals(updatedUser.getAge(), fetchedUser.getAge());
        assertEquals(updatedUser.getTeam(), fetchedUser.getTeam());
    }

    @Test
    public void testRemove() {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteById(userId);

        userService.remove(userId);

        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGenerateUserPairs() {
        List<User> mockOrangeUsers = new LinkedList<>();
        UUID userIdOrange1 = UUID.randomUUID();
        UUID userIdOrange2 = UUID.randomUUID();
        User orangeUser1 = new User(userIdOrange1, "orangename1", "orangesecond1", new Date(2000, 1, 1), "ORANGE_TEAM");
        User orangeUser2 = new User(userIdOrange2, "orangename2", "orangesecond2", new Date(2000, 1, 1), "ORANGE_TEAM");
        mockOrangeUsers.add(orangeUser1);
        mockOrangeUsers.add(orangeUser2);

        List<User> mockPinkUsers = new LinkedList<>();
        UUID userIdPink1 = UUID.randomUUID();
        UUID userIdPink2 = UUID.randomUUID();
        User pinkUser1 = new User(userIdPink1, "pinkname1", "pinksecond1", new Date(2000, 1, 1), "PINK_TEAM");
        User pinkUser2 = new User(userIdPink2, "pinksecond2", "pinksecond2", new Date(2000, 1, 1), "PINK_TEAM");
        mockPinkUsers.add(pinkUser1);
        mockPinkUsers.add(pinkUser2);


        when(userRepository.getAllByTeam("ORANGE_TEAM")).thenReturn(new  LinkedHashSet<>(mockOrangeUsers));
        when(userRepository.getAllByTeam("PINK_TEAM")).thenReturn(new LinkedHashSet<>(mockPinkUsers));

        userService.generateUserPairs();

        List<Map.Entry<User, User>> userPairs = userService.getUserPairs();

        assertNotNull(userPairs);
        assertEquals(2, userPairs.size());

        for (Map.Entry<User, User> pair : userPairs) {
            User orangeUser = pair.getKey();
            User pinkUser = pair.getValue();
            assertEquals("ORANGE_TEAM", orangeUser.getTeam());
            assertEquals("PINK_TEAM", pinkUser.getTeam());
        }

        verify(userRepository, times(1)).getAllByTeam("ORANGE_TEAM");
        verify(userRepository, times(1)).getAllByTeam("PINK_TEAM");
    }
}
