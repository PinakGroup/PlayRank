package com.wzx.service;

/**
 * Created by arthurwang on 17/1/29.
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
