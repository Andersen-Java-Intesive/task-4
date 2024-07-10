package org.example.service.impl;

import org.example.dto.UserDto;
import org.example.mapper.impl.MarkMapperImpl;
import org.example.model.Mark;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.MarkRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.MarkRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    private static UserService instance;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final MarkRepository markRepository = MarkRepositoryImpl.getInstance();

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
        LinkedHashSet<Mark> marks = markRepository.getAll();
        marks.forEach(mark -> {
            if (mark.getUserOneId() == id) {
                mark.setUserOneMark(mark.getUserTwoMark());
                mark.setUserOneId(mark.getUserTwoId());
                markRepository.update(MarkMapperImpl.getInstance().mapMarkToMarkDto(mark));
            }
            if (mark.getUserTwoId() == id) {
                mark.setUserTwoMark(mark.getUserOneMark());
                mark.setUserTwoId(mark.getUserOneId());
                markRepository.update(MarkMapperImpl.getInstance().mapMarkToMarkDto(mark));
            }
            if (mark.getUserOneId() == id && mark.getUserTwoId() == id) {
                markRepository.deleteById(mark.getId());
            }
        });
        userRepository.deleteById(id);
    }


    @Override
    public void generateUserPairs() {
        List<User> orangeTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.ORANGE_TEAM));
        List<User> pinkTeamUsers = new ArrayList<>(userRepository.getAllByTeam(Team.PINK_TEAM));
        Map<Pair<User, User>, Integer> pairHistory = loadPairHistory();

        userPairs = new LinkedList<>();
        pairlessUsers = new LinkedList<>();

        createPairsWithHistory(orangeTeamUsers, pinkTeamUsers, pairHistory);
    }


    private Map<Pair<User, User>, Integer> loadPairHistory() {

        LinkedHashSet<User> allUsers = userRepository.getAll();
        Map<Integer, User> userMap = allUsers.stream().collect(Collectors.toMap(User::getId, user -> user));

        LinkedHashSet<Mark> allMarks = markRepository.getAll();
        Map<Pair<User, User>, Integer> pairHistory = new HashMap<>();

        for (Mark mark : allMarks) {
            User userOne = userMap.get(mark.getUserOneId());
            User userTwo = userMap.get(mark.getUserTwoId());

            if (userOne != null && userTwo != null) {
                Pair<User, User> pair = new Pair<>(userOne, userTwo);
                Pair<User, User> reversedPair = new Pair<>(userTwo, userOne);

                pairHistory.put(pair, pairHistory.getOrDefault(pair, 0) + 1);
                pairHistory.put(reversedPair, pairHistory.getOrDefault(reversedPair, 0) + 1);
            }
        }

        return pairHistory;
    }

    @Override
    public void createPairsWithHistory(List<User> orangeTeamUsers, List<User> pinkTeamUsers, Map<Pair<User, User>, Integer> pairHistory) {
        List<User> smallerTeam = (orangeTeamUsers.size() <= pinkTeamUsers.size()) ? orangeTeamUsers : pinkTeamUsers;
        List<User> largerTeam = (orangeTeamUsers.size() > pinkTeamUsers.size()) ? orangeTeamUsers : pinkTeamUsers;

        Set<User> usedUsers = new HashSet<>();

        for (User user : smallerTeam) {
            User pairUser = getWeightedRandomUser(user, largerTeam, pairHistory);
            userPairs.add(new AbstractMap.SimpleEntry<>(user, pairUser));
            usedUsers.add(pairUser);
        }

        largerTeam.removeAll(usedUsers);
        pairlessUsers.addAll(largerTeam);
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


