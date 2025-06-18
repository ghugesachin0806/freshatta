package com.kisanbasket.freshatta.DTO.product;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientContentDTO {

    private Long id;

    @DecimalMin(value = "0.0", message = "Protein must be non-negative")
    private BigDecimal protein;

    @DecimalMin(value = "0.0", message = "Carbohydrates must be non-negative")
    private BigDecimal carbohydrates;

    @DecimalMin(value = "0.0", message = "Sugars must be non-negative")
    private BigDecimal sugars;

    @DecimalMin(value = "0.0", message = "Dietary fiber must be non-negative")
    private BigDecimal dietaryFiber;

    @DecimalMin(value = "0.0", message = "Saturated fat must be non-negative")
    private BigDecimal saturatedFat;

    @DecimalMin(value = "0.0", message = "Trans fat must be non-negative")
    private BigDecimal transFat;
}
