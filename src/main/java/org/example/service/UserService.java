package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


public interface UserService {

    boolean add(UserDto userDto);

    User find(int id);

    LinkedHashSet<User> findAll();

    void edit(UserDto userDto);

    void remove(int id);

    void generateUserPairs();

    List<Map.Entry<User, User>> getUserPairs();

    List<User> getPairlessUsers();

}
