package com.wzx.repository;

import com.wzx.model.ERole;
import com.wzx.model.Role;
import com.wzx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by arthurwang on 17/2/21.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
