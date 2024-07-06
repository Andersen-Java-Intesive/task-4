package org.example.repository;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.model.enums.Team;

import java.util.LinkedHashSet;

public interface UserRepository {

    boolean create(UserDto userDto);

    User getById(int id);

    LinkedHashSet<User> getAll();

    LinkedHashSet<User> getAllByTeam(Team team);

    void update(UserDto userDto);

    void deleteById(int id);

}
