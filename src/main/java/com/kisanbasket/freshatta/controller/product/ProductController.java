package com.kisanbasket.freshatta.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kisanbasket.freshatta.DTO.product.ImageDTO;
import com.kisanbasket.freshatta.DTO.product.ProductDTO;
import com.kisanbasket.freshatta.service.product.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin/product-manage/product")
public class ProductController {

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public ProductController(ObjectMapper objectMapper, ProductService productService) {
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    // create
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestPart("ProductDTO") String productDTOJson, @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException, MethodArgumentNotValidException {

        ProductDTO productDTO = objectMapper.readValue(productDTOJson, ProductDTO.class);
        ProductDTO productDTO1 = productService.createProduct(productDTO, images);
        return ResponseEntity.ok(productDTO1);
    }

    // getone
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getOneProduct(productId);
        return ResponseEntity.ok(productDTO);
    }

    // getall
    @GetMapping
    public ResponseEntity<?> getAllProductList() {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        return ResponseEntity.ok(productDTOList);
    }

    // getpageable
    @GetMapping("/pageable")
    public ResponseEntity<?> getAllPageableProductList(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                       @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<ProductDTO> productDTOList = productService.getPageableProduct(pageSize, pageNo);
        return ResponseEntity.ok(productDTOList);
    }

    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully."));
    }

    // update
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestPart("ProductDTO") String productDTOJson, @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        ProductDTO productDTO = objectMapper.readValue(productDTOJson, ProductDTO.class);
        ProductDTO productDTO1 = productService.updateproduct(productDTO, images);

        return ResponseEntity.ok(productDTO1);
    }

    // get-image
    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getProductImage(@PathVariable Long imageId) {
        ImageDTO imageDTO = productService.getImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageDTO.getImageType()))
                .body(imageDTO.getImageData());
    }
}
