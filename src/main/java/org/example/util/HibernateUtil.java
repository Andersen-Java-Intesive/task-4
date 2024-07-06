package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties dbProperties = new Properties();
            try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
                dbProperties.load(inputStream);
            }

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", dbProperties.getProperty("DB_URL"));
            configuration.setProperty("hibernate.connection.username", dbProperties.getProperty("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dbProperties.getProperty("DB_PASSWORD"));
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
