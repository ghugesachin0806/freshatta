package com.kisanbasket.freshatta.DTO.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    @NotNull(message = "hasImage flag is required")
    private Boolean hasImage;

    @Size(max = 5_000_000, message = "Image data must not exceed 5MB")
    private byte[] imageData;

    @Size(max = 50, message = "Image type cannot exceed 50 characters")
    private String imageType;

    @Size(max = 100, message = "Image name cannot exceed 100 characters")
    private String imageName;
}