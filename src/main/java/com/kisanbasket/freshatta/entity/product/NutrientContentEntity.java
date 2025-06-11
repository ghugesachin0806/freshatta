package com.kisanbasket.freshatta.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "nutrient_content")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NutrientContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal protein;
    private BigDecimal carbohydrates;
    private BigDecimal sugars;
    private BigDecimal dietaryFiber;
    private BigDecimal saturatedFat;
    private BigDecimal transFat;
}