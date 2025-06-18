package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.CarouselEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarouselRepository extends JpaRepository<CarouselEntity, Long> {
    Optional<CarouselEntity> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
}
