package com.kisanbasket.freshatta.repository.auth;

import com.kisanbasket.freshatta.entity.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
