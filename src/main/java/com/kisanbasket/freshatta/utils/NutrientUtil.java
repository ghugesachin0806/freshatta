package com.kisanbasket.freshatta.utils;

import com.kisanbasket.freshatta.entity.product.NutrientContentEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NutrientUtil {

    public NutrientContentEntity multipleNutrient(NutrientContentEntity nutrientContentEntity, BigDecimal ratio) {
        nutrientContentEntity.setCarbohydrates(nutrientContentEntity.getCarbohydrates() != null ? nutrientContentEntity.getCarbohydrates().multiply(ratio) : null);
        nutrientContentEntity.setSugars(nutrientContentEntity.getSugars() != null ? nutrientContentEntity.getSugars().multiply(ratio) : null);
        nutrientContentEntity.setProtein(nutrientContentEntity.getProtein() != null ? nutrientContentEntity.getProtein().multiply(ratio) : null);
        nutrientContentEntity.setDietaryFiber(nutrientContentEntity.getDietaryFiber() != null ? nutrientContentEntity.getDietaryFiber().multiply(ratio) : null);
        nutrientContentEntity.setTransFat(nutrientContentEntity.getTransFat() != null ? nutrientContentEntity.getTransFat().multiply(ratio) : null);
        nutrientContentEntity.setSaturatedFat(nutrientContentEntity.getSaturatedFat() != null ? nutrientContentEntity.getSaturatedFat().multiply(ratio) : null);

        return nutrientContentEntity;
    }
}
