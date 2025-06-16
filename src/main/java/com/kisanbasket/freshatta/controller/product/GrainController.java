package com.kisanbasket.freshatta.controller.product;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kisanbasket.freshatta.DTO.product.GrainDTO;
import com.kisanbasket.freshatta.service.product.GrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product/grain")
public class GrainController {

    private final GrainService grainService;
    private final ObjectMapper objectMapper;

    public GrainController(GrainService grainService, ObjectMapper objectMapper) {
        this.grainService = grainService;
        this.objectMapper = objectMapper;
    }

    // create grain
    @PostMapping
    public ResponseEntity<?> createGrain(@RequestBody GrainDTO grainDTO) {

        GrainDTO grainDTO1 = grainService.createGrain(grainDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(grainDTO1);
    }

    // read one-grain
    @GetMapping("/{grainId}")
    public ResponseEntity<?> getSingleGrain(@PathVariable Long grainId) {
        GrainDTO grainDTO = grainService.getSingleGrain(grainId);
        return ResponseEntity.ok(grainDTO);
    }

    // get all list
    @GetMapping
    public ResponseEntity<?> getAllGrainList() {
        List<GrainDTO> grainDTOList = grainService.getAllGrainList();
        return ResponseEntity.ok(grainDTOList);
    }

    // get page list
    @GetMapping("/pageable")
    public ResponseEntity<?> getPageableGrainList(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                  @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<GrainDTO> grainDTOList = grainService.getPageableGrainList(pageSize, pageNo);
        return ResponseEntity.ok(grainDTOList);
    }

    // update grain
    @PutMapping
    public ResponseEntity<?> updateGrain(@RequestBody GrainDTO grainDTO) {
        GrainDTO grainDTO1 = grainService.updateGrain(grainDTO);
        return ResponseEntity.ok(grainDTO1);
    }

    // delete grain
    @DeleteMapping("/{grainId}")
    public ResponseEntity<?> deleteGrain(@PathVariable Long grainId) {
        grainService.deletegrain(grainId);
        return ResponseEntity.ok(Map.of("message", "Grain deleted successfully."));
    }

    @GetMapping("/nutrients")
    public ResponseEntity<?> getAllNutrientList() {
        List<String> getFielnames = grainService.getAllNutrientList();
        return ResponseEntity.ok(getFielnames);
    }
}
