package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.GrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrainRepository extends JpaRepository<GrainEntity,Long> {
}
