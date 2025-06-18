package com.kisanbasket.freshatta.DTO.product;

import com.kisanbasket.freshatta.entity.product.ImageEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarouselDTO {
    private Long id;
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    private String imageUrl;
}
