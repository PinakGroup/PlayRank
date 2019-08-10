package com.wzx.service.impl;

import com.wzx.domain.Role;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import com.wzx.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Created by arthurwang on 17/2/22.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void setRole(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    public void save(User user, String roleName) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        setRole(user, roleName);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}