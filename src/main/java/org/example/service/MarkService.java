package org.example.service;

import org.example.dto.MarkDto;
import org.example.dto.UserDto;
import org.example.model.Mark;
import org.example.model.User;

import java.util.LinkedHashSet;

public interface MarkService {
    boolean add(MarkDto markDto);

    Mark find(int id);

    LinkedHashSet<Mark> findAll();

    void edit(MarkDto markDto);

    void remove(int id);
}
