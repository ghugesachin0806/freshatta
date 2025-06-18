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
public class GrainDTO {

    private Long id;

    @NotBlank(message = "Grain name cannot be empty")
    @Size(max = 100, message = "Grain name cannot exceed 100 characters")
    private String grainName;

    @Size(max = 500, message = "Grain description cannot exceed 500 characters")
    private String grainDesc;

    @NotNull(message = "Availability must be specified")
    private Boolean available;

    @NotNull(message = "Actual price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal actualPrice;

    @Valid
    private NutrientContentDTO nutrientContent;

    @Valid
    private List<@NotNull GrainComboDTO> grainComboList;
}
