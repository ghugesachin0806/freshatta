package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity,Long> {
}
