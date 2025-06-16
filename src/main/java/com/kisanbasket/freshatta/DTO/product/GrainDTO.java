package com.kisanbasket.freshatta.DTO.product;

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
    private String grainName;
    private String grainDesc;
    private Boolean available;
    private BigDecimal actualPrice;
    private NutrientContentDTO nutrientContent;
    private List<GrainComboDTO> grainComboList;

}
