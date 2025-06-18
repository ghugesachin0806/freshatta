package com.kisanbasket.freshatta.DTO.product;

import com.kisanbasket.freshatta.entity.product.ImageEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Carousel name cannot be empty")
    @Size(max = 100, message = "Carousel name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String imageUrl;
}
