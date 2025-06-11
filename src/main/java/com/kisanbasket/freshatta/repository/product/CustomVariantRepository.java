package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.CustomVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomVariantRepository extends JpaRepository<CustomVariantEntity,Long> {
    Optional<CustomVariantEntity> findByGrainComboEntityIdAndProductVariantEntityId(Long grainComboId, Long productVariantId);
}
