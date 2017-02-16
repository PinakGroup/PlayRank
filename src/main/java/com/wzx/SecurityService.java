package com.wzx;

/**
 * Created by arthurwang on 17/1/29.
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
