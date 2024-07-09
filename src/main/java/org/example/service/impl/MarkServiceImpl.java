package org.example.service.impl;

import org.example.dto.MarkDto;
import org.example.model.Mark;
import org.example.model.User;
import org.example.repository.MarkRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.MarkRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.MarkService;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MarkServiceImpl implements MarkService {
    private static MarkService instance;
    private final MarkRepository markRepository = MarkRepositoryImpl.getInstance();

    private final UserRepository userRepository = UserRepositoryImpl.getInstance();


    public static synchronized MarkService getInstance() {
        if (instance == null) {
            instance = new MarkServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(MarkDto markDto) {
        return markRepository.create(markDto);
    }

    @Override
    public Mark find(int id) {
        return markRepository.getById(id);
    }

    @Override
    public LinkedHashSet<Mark> findAll() {
        return markRepository.getAll();
    }

    @Override
    public void edit(MarkDto markDto) {
        markRepository.update(markDto);
    }

    @Override
    public void remove(int id) {
        markRepository.deleteById(id);
    }
    @Override
    public LinkedHashSet<Pair<Timestamp, Map<User, Double>>> groupMarksByLessonDate(LinkedHashSet<Mark> allMarks) {
        Map<Timestamp, Map<User, Double>> groupedByDate = new HashMap<>();
        for (Mark mark : allMarks) {
            User userOne = userRepository.getById(mark.getUserOneId());
            User userTwo = userRepository.getById(mark.getUserTwoId());

            Map<User, Double> userMarksMap = groupedByDate.get(mark.getLessonDate());
            if (userMarksMap == null) {
                userMarksMap = new HashMap<>();
                groupedByDate.put(mark.getLessonDate(), userMarksMap);
            }
            userMarksMap.put(userOne, mark.getUserOneMark());
            userMarksMap.put(userTwo, mark.getUserTwoMark());
        }
        LinkedHashSet<Pair<Timestamp, Map<User, Double>>> result = new LinkedHashSet<>();
        for (Map.Entry<Timestamp, Map<User, Double>> entry : groupedByDate.entrySet()) {
            result.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        return result;
    }
    @Override
    public Map<User, Double> getUserTotalScores(LinkedHashSet<Pair<Timestamp, Map<User, Double>>> lessons) {
        LinkedHashSet<User> users = userRepository.getAll();
        Map<User, Double> userTotalScores = new HashMap<>();
        for (User user : users) {
            userTotalScores.put(user, 0.0);
        }
        for (Pair<Timestamp, Map<User, Double>> lesson : lessons) {
            for (Map.Entry<User, Double> entry : lesson.getSecond().entrySet()) {
                User user = entry.getKey();
                Double mark = entry.getValue();
                userTotalScores.put(user, userTotalScores.get(user) + mark);
            }

        }
        return userTotalScores;
    }
}

