package com.wzx.service.impl;

import com.wzx.model.ERole;
import com.wzx.model.Role;
import com.wzx.model.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import com.wzx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by arthurwang on 17/2/22.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void setRole(User user, ERole roleName) {
        Role role = roleRepository.findByName(roleName).orElse(null);
        user.setRoles(Collections.singleton(role));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void createUser(User user, ERole roleName) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        setRole(user, roleName);
        userRepository.save(user);
    }
}