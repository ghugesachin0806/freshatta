package com.kisanbasket.freshatta.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;

    private List<String> imageEntityStringList ;
    private List<ProductVariantDTO> productVariantEntityList;
}
