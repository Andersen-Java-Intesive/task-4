package org.example.service.impl;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class UserServiceImpl implements UserService {

    private static UserService instance;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private List<Map.Entry<User, User>> userPairs;
    private List<User> pairlessUsers;
    public static Map<Pair<User, User>, Integer> pairHistory;


    public UserServiceImpl(Map<Pair<User, User>, Integer> pairHistory) {
        this.pairHistory = pairHistory;
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(pairHistory);
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

    @Override
    public void generateUserPairs() {
        List<User> firstTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.ORANGE_TEAM));
        List<User> secondTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.PINK_TEAM));
        Collections.shuffle(firstTeamUsers);
        Collections.shuffle(secondTeamUsers);
        if (firstTeamUsers.size() < secondTeamUsers.size()) {
            createPairsBySmallerTeam(firstTeamUsers, secondTeamUsers);
        } else {
            createPairsBySmallerTeam(secondTeamUsers, firstTeamUsers);
        }
    }

    @Override
    public void createPairsBySmallerTeam(List<User> smallerTeam, List<User> largerTeam) {
        userPairs = new LinkedList<>();
        Iterator<User> smallerTeamIterator = smallerTeam.iterator();
        Iterator<User> largerTeamIterator = largerTeam.iterator();
        while (smallerTeamIterator.hasNext()) {
            userPairs.add(new AbstractMap.SimpleEntry<>(smallerTeamIterator.next(), largerTeamIterator.next()));
        }
        pairlessUsers = new LinkedList<>();
        while (largerTeamIterator.hasNext()) {
            pairlessUsers.add(largerTeamIterator.next());
        }
    }


    public static User getWeightedRandomUser(User user, List<User> candidates, Map<Pair<User, User>, Integer> pairHistory) {
        List<Double> weights = new ArrayList<>();
        double totalWeight = 0.0;

        for (User candidate : candidates) {
            int historyCount = pairHistory.getOrDefault(new Pair<>(user, candidate), 0);
            double weight = 1.0 / (1 + historyCount);
            weights.add(weight);
            totalWeight += weight;
        }

        double random = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;

        for (int i = 0; i < candidates.size(); i++) {
            cumulativeWeight += weights.get(i);
            if (random < cumulativeWeight) {
                return candidates.get(i);
            }
        }
        throw new RuntimeException("Randomizer error");
    }

    @Override
    public void incrementPairHistory(User user1, User user2) {
        Pair<User, User> pair = new Pair<>(user1, user2);
        pairHistory.put(pair, pairHistory.getOrDefault(pair, 0) + 1);
    }

    @Override
    public List<Map.Entry<User, User>> getUserPairs() {
        return userPairs;
    }

    @Override
    public List<User> getPairlessUsers() {
        return pairlessUsers;
    }

}


