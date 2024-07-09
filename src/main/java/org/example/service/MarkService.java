package org.example.service;

import org.example.dto.MarkDto;
import org.example.dto.UserDto;
import org.example.model.Mark;
import org.example.model.User;
import org.example.service.impl.Pair;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Map;

public interface MarkService {
    boolean add(MarkDto markDto);

    Mark find(int id);

    LinkedHashSet<Mark> findAll();

    void edit(MarkDto markDto);

    void remove(int id);

    LinkedHashSet<Pair<Timestamp, Map<User, Double>>> groupMarksByLessonDate(LinkedHashSet<Mark> allMarks);

    Map<User, Double> getUserTotalScores(LinkedHashSet<Pair<Timestamp, Map<User, Double>>> lessons);
}
