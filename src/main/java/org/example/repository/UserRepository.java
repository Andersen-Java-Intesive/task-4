package org.example.repository;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.List;

public interface UserRepository {

    boolean create(UserDto userDto);

    User getById(int id);

    List<User> getAll();

    void update(UserDto userDto);

    void deleteById(int id);

}
