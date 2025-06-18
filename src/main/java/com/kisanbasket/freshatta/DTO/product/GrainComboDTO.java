package com.kisanbasket.freshatta.DTO.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrainComboDTO {

    private Long id;

    @NotNull(message = "Grain ID is required")
    private Long grainId;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    private BigDecimal weight;

    @NotNull(message = "Actual price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Actual price must be greater than 0")
    private BigDecimal actualPrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling price must be greater than 0")
    private BigDecimal sellingPrice;

    @DecimalMin(value = "0.0", message = "Discounted price must be non-negative")
    private BigDecimal discountedPrice;

    @Valid
    private NutrientContentDTO nutrientContent;
}

