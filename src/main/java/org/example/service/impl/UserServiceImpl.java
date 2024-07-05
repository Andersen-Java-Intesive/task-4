package org.example.service.impl;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.LinkedHashSet;

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

}
