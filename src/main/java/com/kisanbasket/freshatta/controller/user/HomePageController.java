package com.kisanbasket.freshatta.controller.user;

import com.kisanbasket.freshatta.DTO.product.CarouselDTO;
import com.kisanbasket.freshatta.DTO.product.ImageDTO;
import com.kisanbasket.freshatta.DTO.product.ProductDTO;
import com.kisanbasket.freshatta.service.product.CarouselService;
import com.kisanbasket.freshatta.service.product.ProductService;
import com.kisanbasket.freshatta.service.user.HomePageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home-page")
public class HomePageController {

    private final HomePageService homePageService;
    private final ProductService productService;
    private final CarouselService carouselService;

    public HomePageController(HomePageService homePageService, ProductService productService, CarouselService carouselService) {
        this.homePageService = homePageService;
        this.productService = productService;
        this.carouselService = carouselService;
    }

    // get-all
    @GetMapping("/all-product")
    public ResponseEntity<?> getAllProductList() {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        return ResponseEntity.ok(productDTOList);
    }

    // getpageable
    @GetMapping("/product")
    public ResponseEntity<?> getAllPageableProductList(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                       @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<ProductDTO> productDTOList = productService.getPageableProduct(pageSize, pageNo);
        return ResponseEntity.ok(productDTOList);
    }

    // getone
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getOneProduct(productId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/all-carousel")
    public ResponseEntity<?> getAllCarousel() {
        List<CarouselDTO> carouselDTOList = carouselService.getAllCarousel();
        return ResponseEntity.ok(carouselDTOList);
    }
}
