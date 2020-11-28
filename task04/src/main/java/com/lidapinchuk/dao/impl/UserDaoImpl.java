package com.lidapinchuk.dao.impl;

import com.lidapinchuk.dao.UserDao;
import com.lidapinchuk.model.User;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
public class UserDaoImpl implements UserDao {

    private static class UserDaoImplHolder {
        public static final UserDaoImpl instance = new UserDaoImpl();
    }

    public static UserDaoImpl getInstance()  {
        return UserDaoImplHolder.instance;
    }

    private UserDaoImpl() {

    }

    @Override
    public User createUser(User user) {
        log.info("'createActivity' invoked with user: {}", user);

        User createdUser = GeneralDao.getInstance().performOperation(session -> {
            Long newId = (Long) session.save(user);
            user.setInstId(newId);
            return user;
        });

        log.info("'createActivity' returned with createdUser: {}", createdUser);
        return createdUser;
    }

    @Override
    public User updateUser(User newUser) {
        log.info("'updateActivity' invoked with newUser: {}", newUser);

        User updatedUser = GeneralDao.getInstance().performOperation(session -> {
            User user = session.get(User.class, newUser.getInstId());
            user.setUserName(newUser.getUserName())
                    .setLastName(newUser.getLastName())
                    .setEmail(newUser.getEmail())
                    .setEmailBackup(newUser.getEmailBackup())
                    .setTN(newUser.getTN())
                    .setTNBackup(newUser.getTNBackup());
            session.update(user);
            return user;
        });

        log.info("'updateActivity' returned with updatedUser: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("'deleteActivityById' invoked with userId: {}", userId);

        GeneralDao.getInstance().performOperation(session -> {
            User user = session.get(User.class, userId);
            session.delete(user);
        });
    }

    @Override
    public User getUserById(Long userId) {
        log.info("'getActivityById' invoked with userId: {}", userId);

        User user = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("SELECT u FROM User u WHERE u.instId = :userId", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        });

        log.info("'getActivityById' returned with user: {}", user);
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        log.info("'getAllActivities' invoked");

        Collection<User> users = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM User")
                    .list();
        });

        log.info("'getAllActivities' returned with users: {}", users);
        return users;
    }

    @Override
    public BigDecimal getTotalActivitiesPriceByUserId(Long userId) {
        log.info("'getTotalActivitiesPriceByUserId' invoked with userId: {}", userId);

        BigDecimal totalActivitiesPrice = GeneralDao.getInstance().performOperation(session ->
                (BigDecimal) session
                        .createSQLQuery(
                        "SELECT SUM(price * amount) " +
                           "FROM activity " +
                           "WHERE building_id IN " +
                                 "(SELECT inst_id " +
                                 "FROM building " +
                                 "WHERE report_id IN " +
                                      "(SELECT inst_id " +
                                      "FROM report " +
                                      "WHERE user_id = :userId))")
                .setParameter("userId", userId)
                .getSingleResult());

        log.info("'getTotalActivitiesPriceByUserId' returned with totalActivitiesPrice: {}", totalActivitiesPrice);
        return totalActivitiesPrice;
    }
}
