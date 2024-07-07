package org.example.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    private HibernateUtil() {}

    static {
        try {
            Properties dbProperties = new Properties();
            try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
                dbProperties.load(inputStream);
            }

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.url", dbProperties.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username", dbProperties.getProperty("db.username"));
            configuration.setProperty("hibernate.connection.password", dbProperties.getProperty("db.password"));

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
