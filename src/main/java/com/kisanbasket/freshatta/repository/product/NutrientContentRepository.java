package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.NutrientContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NutrientContentRepository extends JpaRepository<NutrientContentEntity,Long> {
}
