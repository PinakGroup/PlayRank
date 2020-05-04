package com.wzx.service;

import com.wzx.model.ERole;
import com.wzx.model.User;

import java.util.Optional;

/**
 * Created by arthurwang on 17/2/22.
 */
public interface UserService {
    void createUser(User user, ERole roleName);

    void setRole(User user, ERole roleName);

    Optional<User> findByUsername(String username);
}
