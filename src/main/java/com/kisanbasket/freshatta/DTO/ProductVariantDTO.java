package com.kisanbasket.freshatta.DTO;

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
    private String name;
    private String description;
    private BigDecimal weight;

    private Long grainComboId;

    private Boolean customMix;

    private Long productId;

    private List<CustomVariantDTO> customVariantEntityList;
}
