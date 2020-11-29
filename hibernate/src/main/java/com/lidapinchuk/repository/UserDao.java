package com.lidapinchuk.repository;

import com.lidapinchuk.model.User;

import java.math.BigDecimal;
import java.util.Collection;

public interface UserDao {

    User createUser(User user);

    User updateUser(User newUser);

    void deleteUserById(Long userId);

    User getUserById(Long userId);

    Collection<User> getAllUsers();

    BigDecimal getTotalActivitiesPriceByUserId(Long userId);

}
