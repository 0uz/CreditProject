package com.payten.credit.repository.user.redis;

import com.payten.credit.service.user.User;

import java.util.Optional;

public interface UserCache {
    Optional<User> retrieveUser(Long userId);
    void createUser(User user);
}
