package com.kisanbasket.freshatta.service.product;


import com.kisanbasket.freshatta.DTO.product.GrainComboDTO;
import com.kisanbasket.freshatta.entity.product.GrainComboEntity;
import com.kisanbasket.freshatta.entity.product.GrainEntity;
import com.kisanbasket.freshatta.entity.product.NutrientContentEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.GrainComboRepository;
import com.kisanbasket.freshatta.repository.product.GrainRepository;
import com.kisanbasket.freshatta.repository.product.NutrientContentRepository;
import com.kisanbasket.freshatta.utils.NutrientUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class GrainComboService {

    private final GrainComboRepository grainComboRepository;
    private final GrainRepository grainRepository;
    private final NutrientContentRepository nutrientContentRepository;
    private final NutrientUtil nutrientUtil;
    private final ModelMapper modelMapper;

    public GrainComboService(GrainComboRepository grainComboRepository, GrainRepository grainRepository, NutrientContentRepository nutrientContentRepository, NutrientUtil nutrientUtil, ModelMapper modelMapper) {
        this.grainComboRepository = grainComboRepository;
        this.grainRepository = grainRepository;
        this.nutrientContentRepository = nutrientContentRepository;
        this.nutrientUtil = nutrientUtil;
        this.modelMapper = modelMapper;
    }

    // create
    public GrainComboDTO createGrainCombo(GrainComboDTO grainComboDTO) {

        GrainEntity grainEntity = grainRepository.findById(grainComboDTO.getGrainId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainComboDTO.getGrainId()));

        NutrientContentEntity nutrientContentEntity = grainEntity.getNutrientContent();
        NutrientContentEntity copyNutrientContentEntity = nutrientContentEntity.toBuilder().id(null).build();

        BigDecimal ratio = grainComboDTO.getWeight().divide(BigDecimal.valueOf(100));

        nutrientUtil.multipleNutrient(copyNutrientContentEntity, ratio);

        GrainComboEntity grainComboEntity = modelMapper.map(grainComboDTO, GrainComboEntity.class);
        grainComboEntity.setGrainEntity(grainEntity);
        grainComboEntity.setNutrientContent(copyNutrientContentEntity);
        GrainComboEntity updatedGrainComboEntity = grainComboRepository.save(grainComboEntity);
        GrainComboDTO updatedGrainComboDTO = modelMapper.map(updatedGrainComboEntity, GrainComboDTO.class);

        return updatedGrainComboDTO;
    }

    // get-one
    public GrainComboDTO getOneGrainCombo(Long grainComboId) {
        GrainComboEntity grainComboEntity = grainComboRepository.findById(grainComboId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + grainComboId));

        GrainComboDTO grainComboDTO = modelMapper.map(grainComboEntity, GrainComboDTO.class);
        return grainComboDTO;
    }

    // get-all
    public List<GrainComboDTO> getAllGrainCombo() {
        List<GrainComboEntity> grainComboEntityList = grainComboRepository.findAll();

        List<GrainComboDTO> grainComboDTOList = grainComboEntityList.stream()
                .map(grainComboEntity -> {
                    GrainComboDTO grainComboDTO = modelMapper.map(grainComboEntity, GrainComboDTO.class);
                    return grainComboDTO;
                }).toList();

        return grainComboDTOList;
    }

    //get-pageable
    public List<GrainComboDTO> getPageableGrainCombo(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<GrainComboEntity> grainComboEntityPage = grainComboRepository.findAll(pageable);

        List<GrainComboEntity> grainComboEntityList = grainComboEntityPage.getContent();

        List<GrainComboDTO> grainComboDTOList = grainComboEntityList.stream()
                .map(grainComboEntity -> {
                    GrainComboDTO grainComboDTO = modelMapper.map(grainComboEntity, GrainComboDTO.class);
                    return grainComboDTO;
                }).toList();

        return grainComboDTOList;
    }

    // delete
    public void deleteGrainCombo(Long grainComboId) {
        GrainComboEntity grainComboEntity = grainComboRepository.findById(grainComboId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + grainComboId));

        grainComboRepository.deleteById(grainComboId);
    }

    // update
    @Transactional
    public GrainComboDTO updateGrainCombo(GrainComboDTO grainComboDTO) {

        GrainComboEntity grainComboEntity = grainComboRepository.findById(grainComboDTO.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + grainComboDTO.getId()));

        // name
        grainComboEntity.setName(grainComboDTO.getName() != null ? grainComboDTO.getName() : grainComboEntity.getName());
        // grainId
        if (grainComboDTO.getGrainId() != null) {
            GrainEntity grainEntity = grainRepository.findById(grainComboDTO.getGrainId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainComboDTO.getGrainId()));
            grainComboEntity.setGrainEntity(grainEntity);
        }
        // weight
        if (grainComboDTO.getWeight() != null) {
            grainComboEntity.setWeight(grainComboDTO.getWeight());
            nutrientContentRepository.deleteById(grainComboEntity.getNutrientContent().getId());

            NutrientContentEntity nutrientContentEntity = nutrientContentRepository.findById(grainComboEntity.getGrainEntity().getNutrientContent().getId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainComboEntity.getGrainEntity().getId()));

            NutrientContentEntity copyNutrientContentEntity =  nutrientContentEntity.toBuilder().id(null).build();

            BigDecimal ratio = grainComboDTO.getWeight().divide(BigDecimal.valueOf(100));

            nutrientUtil.multipleNutrient(copyNutrientContentEntity, ratio);

            grainComboEntity.setNutrientContent(copyNutrientContentEntity);
        }
        // price
        grainComboEntity.setActualPrice(grainComboDTO.getActualPrice() != null ? grainComboDTO.getActualPrice() : grainComboEntity.getActualPrice());
        grainComboEntity.setSellingPrice(grainComboDTO.getSellingPrice() != null ? grainComboDTO.getSellingPrice() : grainComboEntity.getSellingPrice());
        grainComboEntity.setDiscountedPrice(grainComboDTO.getDiscountedPrice() != null ? grainComboDTO.getDiscountedPrice() : grainComboEntity.getDiscountedPrice());

        GrainComboEntity grainComboEntity1 = grainComboRepository.save(grainComboEntity);

        return modelMapper.map(grainComboEntity1, GrainComboDTO.class);
    }
}
