package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.impl.UserServiceImpl;

import java.util.LinkedHashSet;
import java.util.List;


public interface UserService {

    boolean add(UserDto userDto);
    User find(int id);
    LinkedHashSet<User> findAll();
    void edit(UserDto userDto);
    void remove(int id);
    List<UserServiceImpl.Pair<User, User>> generateUserPairs();
    LinkedHashSet<User> getPairlessUsers(List<UserServiceImpl.Pair<User, User>> pairs);
}
