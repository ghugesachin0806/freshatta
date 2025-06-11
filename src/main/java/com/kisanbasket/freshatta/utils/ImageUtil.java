package com.kisanbasket.freshatta.utils;

import com.kisanbasket.freshatta.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageUtil {

    @Value("${spring.image.maxSizeInBytes}")
    private long maxSizeInBytes;

    @Value("${spring.server.baseUrl}")
    private String baseUrl;

    List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/webp");

    public void imageListValidation(MultipartFile[] images) {
        if (images != null && images.length > 0) {

            for (MultipartFile image : images) {
                if (image == null || image.isEmpty()) continue;

                String imageName = image.getOriginalFilename();

                if (!allowedTypes.contains(image.getContentType())) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid content type for image: " + imageName +
                            ". Only JPEG, PNG, and WEBP are allowed.");
                }

                if (image.getSize() > maxSizeInBytes) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Image size too large for: " + imageName +
                            ". Maximum allowed size is 5 MB.");
                }
            }
        }
    }

    public void imageValidation(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            String imageName = image.getOriginalFilename();

            if (!allowedTypes.contains(image.getContentType())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid content type for image: " + imageName +
                        ". Only JPEG, PNG, and WEBP are allowed.");
            }

            if (image.getSize() > maxSizeInBytes) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Image size too large for: " + imageName +
                        ". Maximum allowed size is 5 MB.");
            }
        }
    }

    public String createImageUrl(String customUrl, Long id) {
        return baseUrl + customUrl + id;
    }
}
