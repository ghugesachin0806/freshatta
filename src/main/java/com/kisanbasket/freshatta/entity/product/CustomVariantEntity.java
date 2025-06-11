package com.kisanbasket.freshatta.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomVariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private GrainComboEntity grainComboEntity;

    @ManyToOne
    @JoinColumn
    private ProductVariantEntity productVariantEntity;
}