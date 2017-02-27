package com.wzx.repository;

import com.wzx.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by arthurwang on 17/2/21.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
