package org.example.repo;

import org.example.model.User;

import java.util.Set;

public interface UserRepository  {
    boolean create(User user);
    User findById(int id);
    boolean deleteById(int id);
    boolean update(User user);
    Set<User> all();
}
