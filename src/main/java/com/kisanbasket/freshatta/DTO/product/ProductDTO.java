package com.kisanbasket.freshatta.DTO.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Product description cannot exceed 500 characters")
    private String description;

    private List<String> imageEntityStringList;

    @Valid
    private List<@NotNull ProductVariantDTO> productVariantEntityList;
}