package com.kisanbasket.freshatta.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Product-Variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private GrainComboEntity grainComboEntity;

    private Boolean customMix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProductEntity productEntity;

    @OneToMany(mappedBy = "productVariantEntity", cascade = CascadeType.ALL)
    private List<CustomVariantEntity> customVariantEntityList;
}