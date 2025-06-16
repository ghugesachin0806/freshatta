package com.kisanbasket.freshatta.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private boolean hasImage;
    private byte[] imageData;
    private String imageType;
    private String imageName;
}