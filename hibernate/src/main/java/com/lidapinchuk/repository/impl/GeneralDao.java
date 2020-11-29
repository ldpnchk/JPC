package com.lidapinchuk.repository.impl;

import com.lidapinchuk.exception.DatabaseException;
import com.lidapinchuk.util.HibernateFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class GeneralDao {

    private static class GeneralDaoHolder {
        public static final GeneralDao instance = new GeneralDao();
    }

    private final SessionFactory sessionFactory;

    public static GeneralDao getInstance()  {
        return GeneralDao.GeneralDaoHolder.instance;
    }

    private GeneralDao() {
        this.sessionFactory = HibernateFactory.getSessionFactory();
    }

    public void performOperation(Consumer<Session> operation) {

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            operation.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();

            String message = "Could not perform database operation";
            DatabaseException databaseException = new DatabaseException(message, e);
            log.error(message, databaseException);
            throw databaseException;
        } finally {
            session.close();
        }
    }

    public <T> T performOperation(Function<Session, T> operation) {

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            T result = operation.apply(session);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();

            String message = "Could not perform database operation";
            DatabaseException databaseException = new DatabaseException(message, e);
            log.error(message, databaseException);
            throw databaseException;
        } finally {
            session.close();
        }
    }

}
