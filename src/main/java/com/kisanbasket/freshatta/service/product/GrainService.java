package com.kisanbasket.freshatta.service.product;


import com.kisanbasket.freshatta.DTO.GrainDTO;
import com.kisanbasket.freshatta.entity.product.GrainEntity;
import com.kisanbasket.freshatta.entity.product.NutrientContentEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.GrainRepository;
import com.kisanbasket.freshatta.repository.product.NutrientContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrainService {

    private final ModelMapper modelMapper;
    private final GrainRepository grainRepository;
    private final NutrientContentRepository nutrientContentRepository;

    public GrainService(ModelMapper modelMapper, GrainRepository grainRepository, NutrientContentRepository nutrientContentRepository) {
        this.modelMapper = modelMapper;
        this.grainRepository = grainRepository;
        this.nutrientContentRepository = nutrientContentRepository;
    }

    // create grain
    public GrainDTO createGrain(GrainDTO grainDTO) {

        GrainEntity grainEntity = modelMapper.map(grainDTO, GrainEntity.class);
        NutrientContentEntity nutrientContentEntity = modelMapper.map(grainDTO.getNutrientContent(), NutrientContentEntity.class);
        grainEntity.setNutrientContent(nutrientContentEntity);
        GrainEntity updatedGrainEntity = grainRepository.save(grainEntity);

        GrainDTO updatedGrainDTO = modelMapper.map(updatedGrainEntity, GrainDTO.class);
        updatedGrainDTO.setGrainComboList(null);
        return updatedGrainDTO;
    }


    // get single grain
    public GrainDTO getSingleGrain(Long grainId) {

        GrainEntity grainEntity = grainRepository.findById(grainId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainId));

        GrainDTO grainDTO = modelMapper.map(grainEntity, GrainDTO.class);
        grainDTO.setGrainComboList(null);

        return grainDTO;
    }

    // get all
    public List<GrainDTO> getAllGrainList() {

        List<GrainEntity> grainEntityList = grainRepository.findAll();
        List<GrainDTO> grainDTOList = grainEntityList.stream().
                map(grainEntity -> {
                    GrainDTO grainDTO = modelMapper.map(grainEntity, GrainDTO.class);
                    grainDTO.setGrainComboList(null);
                    return grainDTO;
                }).toList();

        return grainDTOList;
    }

    // get pageable
    public List<GrainDTO> getPageableGrainList(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<GrainEntity> grainEntityPage = grainRepository.findAll(pageable);
        List<GrainEntity> grainEntityList = grainEntityPage.getContent();

        List<GrainDTO> grainDTOList = grainEntityList.stream().
                map(grainEntity -> {
                    GrainDTO grainDTO = modelMapper.map(grainEntity, GrainDTO.class);
                    grainDTO.setGrainComboList(null);
                    return grainDTO;
                }).toList();

        return grainDTOList;
    }

    // update grain
    @Transactional
    public GrainDTO updateGrain(GrainDTO grainDTO) {
        GrainEntity grainEntity = grainRepository.findById(grainDTO.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainDTO.getId()));

        grainEntity.setGrainName(grainDTO.getGrainName() != null ? grainDTO.getGrainName() : grainEntity.getGrainName());
        grainEntity.setGrainDesc(grainDTO.getGrainDesc() != null ? grainDTO.getGrainDesc() : grainEntity.getGrainDesc());
        grainEntity.setAvailable(grainDTO.getAvailable() != null? grainDTO.getAvailable() : grainEntity.isAvailable());
        grainEntity.setActualPrice(grainDTO.getActualPrice() != null ? grainDTO.getActualPrice() : grainEntity.getActualPrice());

        if (grainDTO.getNutrientContent() != null) {
            NutrientContentEntity nutrientContentEntity = modelMapper.map(grainDTO.getNutrientContent(), NutrientContentEntity.class);
            grainEntity.setNutrientContent(nutrientContentEntity);
        }

        GrainEntity updatedGrainEntity = grainRepository.save(grainEntity);
        GrainDTO updatedGrainDTO = modelMapper.map(updatedGrainEntity, GrainDTO.class);

        return updatedGrainDTO;
    }

    // delete grain
    public void deletegrain(Long grainId) {
        grainRepository.findById(grainId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Grain not found with id: " + grainId));
        grainRepository.deleteById(grainId);
    }
}
