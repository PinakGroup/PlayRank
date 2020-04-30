package com.wzx.service;

import com.wzx.domain.User;

import java.util.Optional;

/**
 * Created by arthurwang on 17/2/22.
 */
public interface UserService {
    void createUser(User user, String roleName);

    void setRole(User user, String roleName);

    Optional<User> findByUsername(String username);
}
