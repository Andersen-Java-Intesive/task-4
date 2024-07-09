package org.example.service.impl;

import org.example.dto.MarkDto;
import org.example.model.Mark;
import org.example.model.User;
import org.example.repository.MarkRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.MarkRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.MarkService;
import org.example.service.UserService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class MarkServiceImpl implements MarkService {
    private static MarkService instance;
    private final MarkRepository markRepository = MarkRepositoryImpl.getInstance();

    public static synchronized MarkService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(pairHistory);
        }
        return instance;
    }

    @Override
    public boolean add(MarkDto markDto) {
        return false;
    }

    @Override
    public Mark find(int id) {
        return null;
    }

    @Override
    public LinkedHashSet<Mark> findAll() {
        return null;
    }

    @Override
    public void edit(MarkDto markDto) {

    }

    @Override
    public void remove(int id) {

    }
}
