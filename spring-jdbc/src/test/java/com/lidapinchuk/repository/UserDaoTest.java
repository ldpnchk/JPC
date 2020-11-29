package com.lidapinchuk.repository;

import com.lidapinchuk.config.ApplicationConfig;
import com.lidapinchuk.model.User;
import com.lidapinchuk.repository.impl.JdbcUserRepository;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class UserDaoTest {

    @Autowired
    private JdbcUserRepository jdbcUserRepository;

    @BeforeAll
    public static void initDatabase(@Autowired DataSource dataSource) {

        DatabaseUtil.initializeDatabase(dataSource);
    }

    @BeforeEach
    public void initDatabaseContent(@Autowired DataSource dataSource) {

        DatabaseUtil.fillDatabase(dataSource);
    }


    @Test
    void testCreate() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        int initialUsersSize = jdbcUserRepository.getAllUsers().size();

        jdbcUserRepository.createUser(user);
        Collection<User> users = jdbcUserRepository.getAllUsers();

        assertNotNull(user.getInstId());
        assertTrue(users.contains(user));
        assertEquals(initialUsersSize + 1, users.size());

        jdbcUserRepository.deleteUserById(user.getInstId());
    }

    @Test
    void testUpdate() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        jdbcUserRepository.createUser(user);
        int initialUsersSize = jdbcUserRepository.getAllUsers().size();

        user.setUserName("test")
                .setLastName("test")
                .setEmail("t.e.s.t@test.com")
                .setEmailBackup("test@test@com")
                .setTN("###-##-##")
                .setTNBackup("***-**-**");

        jdbcUserRepository.updateUser(user);

        Collection<User> users = jdbcUserRepository.getAllUsers();
        User updatedUser = jdbcUserRepository.getUserById(user.getInstId());

        assertEquals(user, updatedUser);
        assertEquals(initialUsersSize, users.size());

        jdbcUserRepository.deleteUserById(user.getInstId());
    }

    @Test
    void testDeleteById() {
        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        jdbcUserRepository.createUser(user);
        int initialUsersSize = jdbcUserRepository.getAllUsers().size();

        jdbcUserRepository.deleteUserById(user.getInstId());
        Collection<User> users = jdbcUserRepository.getAllUsers();

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

        jdbcUserRepository.createUser(user);

        User anotherUser = jdbcUserRepository.getUserById(user.getInstId());

        assertEquals(user, anotherUser);

        jdbcUserRepository.deleteUserById(user.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<User> oldUsers = jdbcUserRepository.getAllUsers();

        User user = User.builder()
                .userName("Test")
                .lastName("Test")
                .email("test@test@com")
                .TN("***-**-**")
                .build();

        jdbcUserRepository.createUser(user);

        Collection<User> users = jdbcUserRepository.getAllUsers();

        assertEquals(oldUsers.size() + 1, users.size());
        assertTrue(users.containsAll(oldUsers));
        assertTrue(users.contains(user));

        jdbcUserRepository.deleteUserById(user.getInstId());
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(12686).setScale(2), jdbcUserRepository.getTotalActivitiesPriceByUserId(1L));
        assertEquals(new BigDecimal(660).setScale(2), jdbcUserRepository.getTotalActivitiesPriceByUserId(2L));
    }

}
