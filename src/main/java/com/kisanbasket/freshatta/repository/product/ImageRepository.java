package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long> {
}
