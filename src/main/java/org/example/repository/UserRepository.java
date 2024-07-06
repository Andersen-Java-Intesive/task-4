package org.example.repository;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.LinkedHashSet;

public interface UserRepository  {

    boolean create(UserDto userDto);
    User getById(int id);
    LinkedHashSet<User> getAll();
    void update(UserDto userDto);
    void deleteById(int id);

}
