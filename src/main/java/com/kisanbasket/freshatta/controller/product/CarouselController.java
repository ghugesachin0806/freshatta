package com.kisanbasket.freshatta.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kisanbasket.freshatta.DTO.product.CarouselDTO;
import com.kisanbasket.freshatta.DTO.product.ImageDTO;
import com.kisanbasket.freshatta.service.product.CarouselService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/carousel")
public class CarouselController {

    private final ObjectMapper objectMapper;
    private final CarouselService carouselService;

    public CarouselController(ObjectMapper objectMapper, CarouselService carouselService) {
        this.objectMapper = objectMapper;
        this.carouselService = carouselService;
    }

    @PostMapping
    public ResponseEntity<?> createCarouselImage(@RequestPart("CarouselDTO") String carouselDTOJson, @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        CarouselDTO carouselDTO = objectMapper.readValue(carouselDTOJson, CarouselDTO.class);
        CarouselDTO carouselDTO1 = carouselService.createSingleCarousel(carouselDTO, image);
        return ResponseEntity.ok(carouselDTO1);
    }

    @GetMapping
    public ResponseEntity<?> getAllCarouselList() {
        List<CarouselDTO> carouselDTOList = carouselService.getAllCarousel();
        return ResponseEntity.ok(carouselDTOList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getSingleCarousel(@PathVariable Long orderId) {
        CarouselDTO carouselDTO = carouselService.getSingleCarousel(orderId);
        return ResponseEntity.ok(carouselDTO);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteCarouselByOrderId(@PathVariable Long orderId) {
        carouselService.deleteCarousel(orderId);
        return ResponseEntity.ok("Carousel image deleted successfully..");
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        ImageDTO imageDTO = carouselService.getImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageDTO.getImageType()))
                .body(imageDTO.getImageData());
    }
}
