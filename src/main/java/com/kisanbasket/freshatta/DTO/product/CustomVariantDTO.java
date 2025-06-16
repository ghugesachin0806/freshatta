package com.kisanbasket.freshatta.DTO.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomVariantDTO {
    private Long id;
    private Long grainComboId;
    private Long productVariantId;
}
