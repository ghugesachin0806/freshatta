package com.kisanbasket.freshatta.controller.product;

import com.kisanbasket.freshatta.DTO.CustomVariantDTO;
import com.kisanbasket.freshatta.service.product.CustomVariantservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product-manage/custom-variant")
public class CustomVariantController {

    private final CustomVariantservice customVariantservice;

    public CustomVariantController(CustomVariantservice customVariantservice) {
        this.customVariantservice = customVariantservice;
    }

    @PostMapping
    public ResponseEntity<?> createCustomVariant(@RequestBody CustomVariantDTO customVariantDTO) {
        CustomVariantDTO customVariantDTO1 = customVariantservice.createCustomVariant(customVariantDTO);
        return ResponseEntity.ok(customVariantDTO1);
    }

    @GetMapping("/{customVariantId}")
    public ResponseEntity<?> getCustomVariant(@PathVariable Long customVariantId) {
        CustomVariantDTO customVariantDTO = customVariantservice.getOneCustomVariant(customVariantId);
        return ResponseEntity.ok(customVariantDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomVariant() {
        List<CustomVariantDTO> customVariantDTOList = customVariantservice.getAllCustomVariant();
        return ResponseEntity.ok(customVariantDTOList);
    }

    @GetMapping("/pageable")
    public ResponseEntity<?> getPageableCustomVariant(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                      @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<CustomVariantDTO> customVariantDTOList = customVariantservice.getPageableCustomVariant(pageSize, pageNo);
        return ResponseEntity.ok(customVariantDTOList);

    }

    @DeleteMapping("/{customVariantId}")
    public ResponseEntity<?> deleteCustomVariant(@PathVariable Long customVariantId) {
        customVariantservice.deleteCustomVariant(customVariantId);
        return ResponseEntity.ok(Map.of("message", "CustomVariant deleted successfully."));

    }

    @PutMapping
    public ResponseEntity<?> updateCustomVariant(@RequestBody CustomVariantDTO customVariantDTO) {
        CustomVariantDTO customVariantDTO1 = customVariantservice.createCustomVariant(customVariantDTO);
        return ResponseEntity.ok(customVariantDTO1);
    }
}
