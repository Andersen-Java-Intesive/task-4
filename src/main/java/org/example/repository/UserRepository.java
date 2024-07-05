package org.example.repository;

import org.example.model.User;

import java.util.LinkedHashSet;

public interface UserRepository  {
    boolean create(User user);
    User findById(int id);
    void deleteById(int id);
    void update(User user);
    LinkedHashSet<User> all();
}
