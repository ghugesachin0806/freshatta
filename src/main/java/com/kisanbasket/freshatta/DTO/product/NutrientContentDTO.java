package com.kisanbasket.freshatta.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientContentDTO {
    private Long id;

    private BigDecimal protein;
    private BigDecimal carbohydrates;
    private BigDecimal sugars;
    private BigDecimal dietaryFiber;
    private BigDecimal saturatedFat;
    private BigDecimal transFat;
}
