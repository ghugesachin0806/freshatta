package com.kisanbasket.freshatta.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grain_combo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrainComboEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal weight;

    private BigDecimal actualPrice;
    private BigDecimal sellingPrice;
    private BigDecimal discountedPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NutrientContentEntity nutrientContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private GrainEntity grainEntity;

    @OneToMany(mappedBy = "grainComboEntity", cascade = CascadeType.ALL)
    private List<ProductVariantEntity> productVariantEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "grainComboEntity", cascade = CascadeType.ALL)
    private List<CustomVariantEntity> customVariantEntityList;
}