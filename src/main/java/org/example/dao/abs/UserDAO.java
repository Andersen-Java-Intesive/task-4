package org.example.dao.abs;

import org.example.model.User;

import java.util.Set;

public interface UserDAO  {
    boolean create(User user);
    User findById(int id);
    boolean deleteById(int id);
    boolean update(User user);
    Set<User> all();
}
