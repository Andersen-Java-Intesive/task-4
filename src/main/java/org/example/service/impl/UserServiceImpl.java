package org.example.service.impl;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.AbstractMap;


public class UserServiceImpl implements UserService {

    private static UserService instance;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private List<Map.Entry<User, User>> userPairs;
    private List<User> pairlessUsers;

    private UserServiceImpl() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(UserDto userDto) {
        return userRepository.create(userDto);
    }

    @Override
    public User find(int id) {
        return userRepository.getById(id);
    }

    @Override
    public LinkedHashSet<User> findAll() {
        return userRepository.getAll();
    }

    @Override
    public void edit(UserDto userDto) {
        userRepository.update(userDto);
    }

    @Override
    public void remove(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void generateUserPairs() {
        List<User> firstTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.ORANGE_TEAM));
        List<User> secondTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.PINK_TEAM));
        Collections.shuffle(firstTeamUsers);
        Collections.shuffle(secondTeamUsers);
        if (firstTeamUsers.size() < secondTeamUsers.size()) {
            createPairsBySmallerTeam(firstTeamUsers, secondTeamUsers);
        } else {
            createPairsBySmallerTeam(secondTeamUsers, firstTeamUsers);
        }
    }

    private void createPairsBySmallerTeam(List<User> smallerTeam, List<User> largerTeam) {
        userPairs = new LinkedList<>();
        Iterator<User> smallerTeamIterator = smallerTeam.iterator();
        Iterator<User> largerTeamIterator = largerTeam.iterator();
        while (smallerTeamIterator.hasNext()) {
            userPairs.add(new AbstractMap.SimpleEntry<>(smallerTeamIterator.next(), largerTeamIterator.next()));
        }
        pairlessUsers = new LinkedList<>();
        while (largerTeamIterator.hasNext()) {
            pairlessUsers.add(largerTeamIterator.next());
        }
    }

    @Override
    public List<Map.Entry<User, User>> getUserPairs() {
        return userPairs;
    }

    @Override
    public List<User> getPairlessUsers() {
        return pairlessUsers;
    }

}
