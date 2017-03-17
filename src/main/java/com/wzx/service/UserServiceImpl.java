package com.wzx.service;

import com.wzx.domain.Role;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by arthurwang on 17/2/22.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public void setRole(User user, String roleName) {
        Role role = new Role(roleName);
        roleRepository.save(role);
        user.setRoles(Arrays.asList(role));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}