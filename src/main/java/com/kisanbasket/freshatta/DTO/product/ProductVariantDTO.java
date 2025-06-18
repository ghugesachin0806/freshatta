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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {

    private Long id;

    @NotBlank(message = "Variant name cannot be empty")
    @Size(max = 100, message = "Variant name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    private BigDecimal weight;

    private Long grainComboId;

    @NotNull(message = "Custom mix flag must be specified")
    private Boolean customMix;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @Valid
    private List<@NotNull CustomVariantDTO> customVariantEntityList;
}
