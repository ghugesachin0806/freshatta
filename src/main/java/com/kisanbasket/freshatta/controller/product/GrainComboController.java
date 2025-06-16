package com.kisanbasket.freshatta.controller.product;


import com.kisanbasket.freshatta.DTO.product.GrainComboDTO;
import com.kisanbasket.freshatta.service.product.GrainComboService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product/grain-combo")
public class GrainComboController {

    private final GrainComboService grainComboService;

    public GrainComboController(GrainComboService grainComboService) {
        this.grainComboService = grainComboService;
    }

    // create
    @PostMapping
    public ResponseEntity<?> createGrainCombo(@RequestBody GrainComboDTO grainComboDTO) {
        GrainComboDTO grainComboDTO1 = grainComboService.createGrainCombo(grainComboDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(grainComboDTO1);
    }

    // get-one
    @GetMapping("/{grainComboId}")
    public ResponseEntity<?> getGrainCombo(@PathVariable Long grainComboId) {
        GrainComboDTO grainComboDTO = grainComboService.getOneGrainCombo(grainComboId);
        return ResponseEntity.ok(grainComboDTO);
    }

    // get-all
    @GetMapping
    public ResponseEntity<?> getAllGrainCombo() {
        List<GrainComboDTO> grainComboDTOList = grainComboService.getAllGrainCombo();
        return ResponseEntity.ok(grainComboDTOList);
    }

    //get-pageable
    @GetMapping("/pageable")
    public ResponseEntity<?> getPageableGrainCombo(@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
                                                   @RequestParam(value = "page_no", defaultValue = "0", required = false) int pageNo) {
        List<GrainComboDTO> grainComboDTOList = grainComboService.getPageableGrainCombo(pageSize, pageNo);
        return ResponseEntity.ok(grainComboDTOList);
    }

    // delete
    @DeleteMapping("/{grainComboId}")
    public ResponseEntity<?> deleteGrainCombo(@PathVariable Long grainComboId) {
        grainComboService.deleteGrainCombo(grainComboId);
        return ResponseEntity.ok(Map.of("message", "GrainCombo deleted successfully."));
    }

    // update
    @PutMapping
    public ResponseEntity<?> updateGrainCombo(@RequestBody GrainComboDTO grainComboDTO) {
        GrainComboDTO grainComboDTO1 = grainComboService.updateGrainCombo(grainComboDTO);
        return ResponseEntity.ok(grainComboDTO1);
    }
}
