package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.List;

public interface UserService {
    boolean add(UserDto userDto);
    User find(int id);
    List<User> findAll();
    void edit(UserDto userDto);
    void remove(int id);
}
