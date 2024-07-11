//package org.example.service;
//
//import org.example.dto.UserDto;
//import org.example.model.User;
//import org.example.model.enums.Team;
//import org.example.repository.UserRepository;
//import org.example.repository.impl.UserRepositoryImpl;
//import org.example.service.impl.UserServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.ArgumentCaptor;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//
//import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.anyInt;
//import static org.mockito.Mockito.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({UserServiceImpl.class, UserRepositoryImpl.class, DatabaseService.class})
//@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
//public class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private DatabaseService mockDatabaseService;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private Connection mockConnection;
//    private PreparedStatement mockPreparedStatement;
//
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        PowerMockito.mockStatic(UserRepositoryImpl.class);
//        Whitebox.setInternalState(UserRepositoryImpl.class, "databaseService", mockDatabaseService);
//        Whitebox.setInternalState(DatabaseService.class, "instance", mockDatabaseService);
//
//        mockConnection = mock(Connection.class);
//        mockPreparedStatement = mock(PreparedStatement.class);
//        when(mockDatabaseService.getConnection(anyInt())).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//    }
//    @Test
//    public void testAdd() throws SQLException {
//        UserDto userDto = new UserDto();
//        userDto.setFirstName("Nurdos");
//        userDto.setSecondName("Ramazan");
//        userDto.setAge(21);
//        userDto.setTeam("PINK_TEAM");
//
//        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = userService.add(userDto);
//
//        assertTrue(result);
//        verify(mockDatabaseService).getConnection(TRANSACTION_READ_UNCOMMITTED);
//        verify(mockConnection).setAutoCommit(false);
//        verify(mockConnection).commit();
//        verify(mockPreparedStatement).setString(1, "Nurdos");
//        verify(mockPreparedStatement).setString(2, "Ramazan");
//        verify(mockPreparedStatement).setInt(3, 21);
//        verify(mockPreparedStatement).setString(4, "PINK_TEAM");
//        verify(mockPreparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testFind() {
//        Whitebox.setInternalState(userService, "userRepository", userRepository);
//
//        User mockUser = new User();
//        mockUser.setId(123);
//        mockUser.setFirstName("Nurdos");
//        mockUser.setSecondName("Ramazan");
//        mockUser.setAge(21);
//        mockUser.setTeam(Team.PINK_TEAM);
//
//        when(userRepository.getById(123)).thenReturn(mockUser);
//
//        User foundUser = userService.find(123);
//        assertNotNull(foundUser);
//        assertEquals("Nurdos", foundUser.getFirstName());
//        assertEquals("Ramazan", foundUser.getSecondName());
//        assertEquals(21, foundUser.getAge());
//        assertEquals(Team.PINK_TEAM, foundUser.getTeam());
//    }
//
//    @Test
//    public void testFindAll() {
//        LinkedHashSet<User> mockUsers = new LinkedHashSet<>();
//        User user1 = new User(10, "name1", "second1", 30, Team.ORANGE_TEAM);
//        User user2 = new User(35, "name2", "second2", 25, Team.PINK_TEAM);
//        mockUsers.add(user1);
//        mockUsers.add(user2);
//
//        when(userRepository.getAll()).thenReturn(mockUsers);
//        Whitebox.setInternalState(userService, "userRepository", userRepository);
//
//
//        LinkedHashSet<User> foundUsers = userService.findAll();
//
//        assertNotNull(foundUsers);
//        assertEquals(2, foundUsers.size());
//
//        for (User user : foundUsers) {
//            switch (user.getId()) {
//                case 10:
//                    assertEquals("name1", user.getFirstName());
//                    assertEquals("second1", user.getSecondName());
//                    assertEquals(30, user.getAge());
//                    assertEquals(Team.ORANGE_TEAM, user.getTeam());
//                    break;
//                case 35:
//                    assertEquals("name2", user.getFirstName());
//                    assertEquals("second2", user.getSecondName());
//                    assertEquals(25, user.getAge());
//                    assertEquals(Team.PINK_TEAM, user.getTeam());
//                    break;
//                default:
//                    fail("Unexpected user ID: " + user.getId());
//            }
//        }
//
//        verify(userRepository, times(1)).getAll();
//    }
//    @Test
//    public void testEdit() {
//        int userId = 1;
//        UserDto updatedUserDto = new UserDto(userId, "newName", "newSecond", 18, "ORANGE_TEAM");
//        Whitebox.setInternalState(userService, "userRepository", userRepository);
//
//        User existingUser = new User(userId, "exisingName", "existings", 25, Team.PINK_TEAM);
//        when(userRepository.getById(userId)).thenReturn(existingUser);
//
//        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
//        doNothing().when(userRepository).update(userDtoCaptor.capture());
//
//        userService.edit(updatedUserDto);
//
//        verify(userRepository, times(1)).update(any(UserDto.class));
//
//        User updatedUser = new User(userId, "newName", "newSecond", 18, Team.ORANGE_TEAM);
//        when(userRepository.getById(userId)).thenReturn(updatedUser);
//        User fetchedUser = userRepository.getById(userId);
//        assertEquals(updatedUser.getFirstName(), fetchedUser.getFirstName());
//        assertEquals(updatedUser.getSecondName(), fetchedUser.getSecondName());
//        assertEquals(updatedUser.getAge(), fetchedUser.getAge());
//        assertEquals(updatedUser.getTeam(), fetchedUser.getTeam());
//    }
//
//    @Test
//    public void testRemove() {
//        int userId = 1;
//        doNothing().when(userRepository).deleteById(userId);
//        Whitebox.setInternalState(userService, "userRepository", userRepository);
//
//        userService.remove(userId);
//
//        verify(userRepository).deleteById(userId);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    public void testGenerateUserPairs() {
//        Whitebox.setInternalState(userService, "userRepository", userRepository);
//
//        LinkedHashSet<User> mockOrangeUsers = new LinkedHashSet<>();
//        User orangeUser1 = new User(1, "orangename1", "orangesecond1", 30, Team.ORANGE_TEAM);
//        User orangeUser2 = new User(2, "orangename2", "orangesecond2", 25, Team.ORANGE_TEAM);
//        mockOrangeUsers.add(orangeUser1);
//        mockOrangeUsers.add(orangeUser2);
//
//        LinkedHashSet<User> mockPinkUsers = new LinkedHashSet<>();
//        User pinkUser1 = new User(3, "pinkname1", "pinksecond1", 28, Team.PINK_TEAM);
//        User pinkUser2 = new User(4, "pinksecond2", "pinksecond2", 26, Team.PINK_TEAM);
//        mockPinkUsers.add(pinkUser1);
//        mockPinkUsers.add(pinkUser2);
//
//        when(userRepository.getAllByTeam(Team.ORANGE_TEAM)).thenReturn(mockOrangeUsers);
//        when(userRepository.getAllByTeam(Team.PINK_TEAM)).thenReturn(mockPinkUsers);
//
//        userService.generateUserPairs();
//
//        List<Map.Entry<User, User>> userPairs = userService.getUserPairs();
//        assertNotNull(userPairs);
//        assertEquals(2, userPairs.size());
//
//        for (Map.Entry<User, User> pair : userPairs) {
//            if (pair.getKey().getTeam() == Team.ORANGE_TEAM) {
//                assertEquals(Team.PINK_TEAM, pair.getValue().getTeam());
//            } else {
//                assertEquals(Team.ORANGE_TEAM, pair.getValue().getTeam());
//            }
//        }
//
//        List<User> pairlessUsers = userService.getPairlessUsers();
//        assertNotNull(pairlessUsers);
//        assertEquals(0, pairlessUsers.size());
//
//        verify(userRepository, times(1)).getAllByTeam(Team.ORANGE_TEAM);
//        verify(userRepository, times(1)).getAllByTeam(Team.PINK_TEAM);
//    }
//
//
//}