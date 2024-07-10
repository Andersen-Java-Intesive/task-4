package org.example.service;

import org.example.dto.MarkDto;
import org.example.dto.UserDto;
import org.example.model.Mark;
import org.example.model.User;
import org.example.service.impl.Pair;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;

public interface MarkService {
    boolean add(MarkDto markDto);

    Mark find(UUID id);

    LinkedHashSet<Mark> findAll();

    void edit(MarkDto markDto);

    void remove(UUID id);

    LinkedHashSet<Pair<Date, Map<User, Double>>> groupMarksByLessonDate(LinkedHashSet<Mark> allMarks);

    Map<User, Double> getUserTotalScores(LinkedHashSet<Pair<Date, Map<User, Double>>> lessons);
}
