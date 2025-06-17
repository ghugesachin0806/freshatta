package com.kisanbasket.freshatta.repository.auth;

import com.kisanbasket.freshatta.entity.auth.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
