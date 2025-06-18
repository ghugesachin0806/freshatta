package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity,Long> {
    Optional<ProductVariantEntity> findByNameAndProductEntityId(String name, Long productId);
}
