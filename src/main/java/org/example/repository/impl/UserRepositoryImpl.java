package org.example.repository.impl;

import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.mapper.impl.UserMapperImpl;
import org.example.model.User;
import org.example.model.enums.Team;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.LinkedHashSet;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl instance;
    private final UserMapper userMapper = UserMapperImpl.getInstance();
    private final SessionFactory sessionFactory;

    private UserRepositoryImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public boolean create(UserDto userDto) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = userMapper.mapUserDtoToUser(userDto);
            session.save(user);

            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public LinkedHashSet<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            return new LinkedHashSet<>(users);
        }
    }

    @Override
    public LinkedHashSet<User> getAllByTeam(Team team) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("from User where team = :team", User.class)
                    .setParameter("team", team)
                    .list();
            return new LinkedHashSet<>(users);
        }
    }

    @Override
    public void update(UserDto userDto) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = userMapper.mapUserDtoToUser(userDto);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

}
