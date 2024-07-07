package org.example.repository;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.model.enums.Team;

import java.util.List;

public interface UserRepository {

    boolean create(UserDto userDto);

    User getById(Long id);

    List<User> getAll();

    List<User> getAllByTeam(Team team);

    void update(UserDto userDto);

    void deleteById(Long id);

}
