package com.kisanbasket.freshatta.repository.product;

import com.kisanbasket.freshatta.entity.product.GrainComboEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GrainComboRepository extends JpaRepository<GrainComboEntity,Long> {
    Optional<GrainComboEntity> findByNameAndGrainEntityId(String name, Long grainId);
}
