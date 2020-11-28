package com.lidapinchuk.dao;

import com.lidapinchuk.dao.impl.UserDaoImpl;
import com.lidapinchuk.model.User;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private static UserDao userDao;

    @BeforeAll
    public static void initDatabase() {

        DatabaseUtil.initializeDatabase();

        userDao = UserDaoImpl.getInstance();
    }

    @BeforeEach
    public void initDatabaseContent() {

        DatabaseUtil.fillDatabase();
    }

    @Test
    void testCreate() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        int initialUsersSize = userDao.getAllUsers().size();

        userDao.createUser(user);
        Collection<User> users = userDao.getAllUsers();

        assertNotNull(user.getInstId());
        assertTrue(users.contains(user));
        assertEquals(initialUsersSize + 1, users.size());

        userDao.deleteUserById(user.getInstId());
    }

    @Test
    void testUpdate() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        userDao.createUser(user);
        int initialUsersSize = userDao.getAllUsers().size();

        user.setUserName("test")
                .setLastName("test")
                .setEmail("t.e.s.t@test.com")
                .setEmailBackup("test@test@com")
                .setTN("###-##-##")
                .setTNBackup("***-**-**");

        userDao.updateUser(user);

        Collection<User> users = userDao.getAllUsers();
        User updatedUser = userDao.getUserById(user.getInstId());

        assertEquals(user, updatedUser);
        assertEquals(initialUsersSize, users.size());

        userDao.deleteUserById(user.getInstId());
    }

    @Test
    void testDeleteById() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        userDao.createUser(user);
        int initialUsersSize = userDao.getAllUsers().size();

        userDao.deleteUserById(user.getInstId());
        Collection<User> users = userDao.getAllUsers();

        assertFalse(users.contains(user));
        assertEquals(initialUsersSize - 1, users.size());
    }

    @Test
    void testGetById() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        userDao.createUser(user);

        User anotherUser = userDao.getUserById(user.getInstId());

        assertEquals(user, anotherUser);
    }

    @Test
    void testGetAll() {

        Collection<User> oldUsers = userDao.getAllUsers();

        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        userDao.createUser(user);

        Collection<User> users = userDao.getAllUsers();

        assertEquals(oldUsers.size() + 1, users.size());
        assertTrue(users.containsAll(oldUsers));
        assertTrue(users.contains(user));
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(12686).setScale(2), userDao.getTotalActivitiesPriceByUserId(1L));
        assertEquals(new BigDecimal(660).setScale(2), userDao.getTotalActivitiesPriceByUserId(2L));
    }

}
