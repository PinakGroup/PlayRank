package com.wzx;

import com.wzx.model.ERole;
import com.wzx.model.Role;
import com.wzx.model.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInit(UserRepository userRepository,
                    RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void dataInit() {
        User admin = new User("admin",
                "admin@test.com",
                passwordEncoder.encode("12"));

        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(adminRole);
        Role userRole = new Role(ERole.ROLE_USER);
        roleRepository.save(userRole);
        Role modRole = new Role(ERole.ROLE_MODERATOR);
        roleRepository.save(modRole);

        admin.setRoles(Collections.singleton(adminRole));

        userRepository.save(admin);
    }
}
