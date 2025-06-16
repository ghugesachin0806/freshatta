package com.kisanbasket.freshatta.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrainComboDTO {
    private Long id;
    private Long grainId;
    private String name;
    private BigDecimal weight;
    private BigDecimal actualPrice;
    private BigDecimal sellingPrice;
    private BigDecimal discountedPrice;
    private NutrientContentDTO nutrientContent;
}
