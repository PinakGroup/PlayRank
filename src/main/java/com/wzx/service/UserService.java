package com.wzx.service;

import com.wzx.domain.User;

/**
 * Created by arthurwang on 17/2/22.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
