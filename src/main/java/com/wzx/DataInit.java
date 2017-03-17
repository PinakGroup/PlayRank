package com.wzx;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.wzx.domain.Role;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arthurwang on 17/3/1.
 */
@Service
public class DataInit {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Transactional
    private Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    @PostConstruct
    public void dataInit(){
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("12"));
        admin.setPasswordConfirm(bCryptPasswordEncoder.encode("12"));

        Role userRole = new Role("ADMIN");
        roleRepository.save(userRole);
        admin.setRoles(Arrays.asList(userRole));

        userRepository.save(admin);
    }
}
