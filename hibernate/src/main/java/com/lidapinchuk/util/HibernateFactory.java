package com.lidapinchuk.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static java.util.Objects.isNull;

public class HibernateFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (isNull(sessionFactory)) {
            sessionFactory = new Configuration()
                    .configure("hibernate/hibernate.cfg.xml")
                    .buildSessionFactory();
        }

        return sessionFactory;
    }

}
