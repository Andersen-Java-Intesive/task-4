package org.example.service.impl;

import org.example.dto.UserDto;
import org.example.model.Team;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;


public class UserServiceImpl implements UserService {

    private static UserService instance;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

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
    public List<Pair<User, User>> generateUserPairs() {
        List<Pair<User, User>> userPairs = new ArrayList<>();
        LinkedHashSet<User> allUsers = userRepository.getAll();
        List<User> teamOneUsers = new ArrayList<>();
        List<User> teamTwoUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getTeam() == Team.TEAM_ONE) {
                teamOneUsers.add(user);
            } else if (user.getTeam() == Team.TEAM_TWO) {
                teamTwoUsers.add(user);
            }
        }
        Collections.shuffle(teamOneUsers);
        Collections.shuffle(teamTwoUsers);
        for (int i = 0; i < Math.min(teamOneUsers.size(), teamTwoUsers.size()); i++) {
            userPairs.add(new  Pair<>(teamOneUsers.get(i), teamTwoUsers.get(i)));
        }
        return userPairs;
    }

    @Override
    public LinkedHashSet<User> getPairlessUsers(List<Pair<User, User>> pairs) {
        LinkedHashSet<User> allUsers = userRepository.getAll();
        if (pairs.size()*2 == allUsers.size()){
            return new LinkedHashSet<>();
        }
        pairs.forEach(userUserPair -> {
            allUsers.remove(userUserPair.getFirst());
            allUsers.remove(userUserPair.getSecond());
        });
        return allUsers;
    }

    public static class Pair<F, S> {
        private final F first;
        private final S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
    }

}
