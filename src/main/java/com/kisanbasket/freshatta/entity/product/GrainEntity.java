package com.kisanbasket.freshatta.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grainName;
    private String grainDesc;

    private boolean available;

    private BigDecimal actualPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NutrientContentEntity nutrientContent;

    @OneToMany(mappedBy = "grainEntity", cascade = CascadeType.ALL)
    private List<GrainComboEntity> grainComboList= new ArrayList<>();
}