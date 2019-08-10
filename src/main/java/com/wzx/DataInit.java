package com.wzx;

import com.wzx.domain.Role;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by arthurwang on 17/3/1.
 */
@Service
public class DataInit {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataInit(UserRepository userRepository,
                    RoleRepository roleRepository,
                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void dataInit() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("12"));
        admin.setPasswordConfirm(bCryptPasswordEncoder.encode("12"));

        Role adminRole = new Role("ADMIN");
        roleRepository.save(adminRole);
        Role userRole = new Role("USER");
        roleRepository.save(userRole);

        admin.setRoles(Collections.singletonList(adminRole));

        userRepository.save(admin);
    }
}
