package org.example.repository.impl;

import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.mapper.UserMapperImpl;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepository instance;
    private final UserMapper userMapper = UserMapperImpl.getInstance();
    private final SessionFactory sessionFactory;

    private UserRepositoryImpl() {
        sessionFactory = createSessionFactory();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new IOException("Sorry, unable to find database.properties");
            }
            properties.load(input);

            configuration.setProperty("hibernate.connection.url", properties.getProperty("DB_URL"));
            configuration.setProperty("hibernate.connection.username", properties.getProperty("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", properties.getProperty("DB_PASSWORD"));
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load database properties", ex);
        }
        configuration.configure("hibernate.cfg.xml");
        return configuration.buildSessionFactory();
    }

    @Override
    public boolean create(UserDto userDto) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = userMapper.mapDtoToUser(userDto);
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                return user;
            } else {
                throw new RuntimeException("User with id " + id + " not found");
            }
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void update(UserDto userDto) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userDto.getId());
            if (user != null) {
                userMapper.updateUserFromDto(user, userDto);
                session.update(user);
                transaction.commit();
            }
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
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
