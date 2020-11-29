package com.lidapinchuk.repository;

import com.lidapinchuk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

public class JpaUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User createUser(User user) {

        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(User user) {

        entityManager.merge(user);
        return user;
    }

    @Transactional
    public void deleteUserById(Long userId) {

        User user = entityManager.find(User.class, userId);
        entityManager.remove(user);
    }

    @Transactional
    public User getUserById(Long userId) {

        return entityManager.find(User.class, userId);
    }

    @Transactional
    public Collection<User> getAllUsers() {

        return entityManager
                    .createQuery("FROM User").getResultList();
    }

}
