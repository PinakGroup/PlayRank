package com.wzx.service;

import com.wzx.domain.User;

/**
 * Created by arthurwang on 17/2/22.
 */
public interface UserService {
    void save(User user, String roleName);
    void setRole(User user, String roleName);

    User findByUsername(String username);
}
