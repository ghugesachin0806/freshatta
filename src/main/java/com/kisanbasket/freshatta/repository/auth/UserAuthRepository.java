package com.kisanbasket.freshatta.repository.auth;

import com.kisanbasket.freshatta.entity.auth.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity,Long> {
    Optional<UserAuthEntity> findByMobileNumber(String username);
}
