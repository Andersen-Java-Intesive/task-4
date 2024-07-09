package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import org.example.model.User;
import org.example.model.enums.Team;
import org.example.service.impl.Pair;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserPairGeneratorTest {
    private User user1;
    private User candidate1;
    private User candidate2;
    private User candidate3;

    private Map<Pair<User, User>, Integer> pairHistory;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        candidate1 = new User(1,"Candidate1", "new", 25, Team.ORANGE_TEAM);
        candidate2 = new User(2,"Candidate2","new", 25, Team.ORANGE_TEAM);
        candidate3 = new User(3,"Candidate3","new", 25, Team.ORANGE_TEAM);

        pairHistory = new HashMap<>();
        pairHistory.put(new Pair<>(user1, candidate1), 1);
        pairHistory.put(new Pair<>(user1, candidate2), 2);
        pairHistory.put(new Pair<>(user1, candidate3), 3);
    }

    @Test
    public void testGetWeightedRandomUser() {
        List<User> candidates = Arrays.asList(candidate1, candidate2, candidate3);
        int candidate1Count = 0;
        int candidate2Count = 0;
        int candidate3Count = 0;
        int iterations = 1_000_000;

        for (int i = 0; i < iterations; i++) {
            User selectedCandidate = UserServiceImpl.getWeightedRandomUser(user1, candidates, pairHistory);
            if (selectedCandidate.equals(candidate1)) {
                candidate1Count++;
            } else if (selectedCandidate.equals(candidate2)) {
                candidate2Count++;
            } else if (selectedCandidate.equals(candidate3)) {
                candidate3Count++;
            }
        }

        System.out.println("Candidate1 selected: " + candidate1Count + " times");
        System.out.println("Candidate2 selected: " + candidate2Count + " times");
        System.out.println("Candidate3 selected: " + candidate3Count + " times");

        assertTrue(candidate1Count > 0, "Candidate1 should be selected at least once");
        assertTrue(candidate2Count > 0, "Candidate2 should be selected at least once");
        assertTrue(candidate3Count > 0, "Candidate3 should be selected at least once");


        assertTrue(candidate1Count > candidate2Count, "Candidate3 should be selected more frequently than Candidate1");
        assertTrue(candidate2Count > candidate3Count, "Candidate1 should be selected more frequently than Candidate2");
    }

    // Вспомогательный метод для тестирования




}
