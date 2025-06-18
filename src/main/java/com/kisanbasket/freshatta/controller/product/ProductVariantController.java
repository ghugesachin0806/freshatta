package com.kisanbasket.freshatta.controller.product;

import com.kisanbasket.freshatta.DTO.product.ProductVariantDTO;
import com.kisanbasket.freshatta.service.product.ProductVariantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product-manage/product-variant")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    // create
    @PostMapping
    public ResponseEntity<?> createProductVariant(@Valid @RequestBody ProductVariantDTO productVariantDTO) {
        ProductVariantDTO productVariantDTO1 = productVariantService.createProductVariant(productVariantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productVariantDTO1);
    }

    // get-one
    @GetMapping("/{productVariantId}")
    public ResponseEntity<?> getProductVariant(@PathVariable Long productVariantId) {
        ProductVariantDTO productVariantDTO = productVariantService.getProductVariant(productVariantId);
        return ResponseEntity.ok(productVariantDTO);
    }

    // get-all
    @GetMapping
    public ResponseEntity<?> getAllProductVariant() {
        List<ProductVariantDTO> productVariantDTOList = productVariantService.getAllProductvariantList();
        return ResponseEntity.ok(productVariantDTOList);
    }

    // get-pageable
    @GetMapping("/pageable")
    public ResponseEntity<?> getPageableProductVariantList(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                           @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<ProductVariantDTO> productVariantDTOList = productVariantService.getAllProductVariantPageable(pageSize, pageNo);
        return ResponseEntity.ok(productVariantDTOList);
    }

    // update
    @PutMapping
    public ResponseEntity<?> updateProductVariant(@RequestBody ProductVariantDTO productVariantDTO) {
        ProductVariantDTO productVariantDTO1 = productVariantService.updateProductVariant(productVariantDTO);
        return ResponseEntity.ok(productVariantDTO1);
    }

    // delete
    @DeleteMapping("/{productVariantId}")
    public ResponseEntity<?> deleteProductVariant(@PathVariable Long productVariantId) {
        productVariantService.deleteProductVariant(productVariantId);
        return ResponseEntity.ok(Map.of("message", "Product-variant deleted successfully."));
    }
}
