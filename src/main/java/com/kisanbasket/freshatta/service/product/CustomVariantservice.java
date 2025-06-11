package com.kisanbasket.freshatta.service.product;


import com.kisanbasket.freshatta.DTO.CustomVariantDTO;
import com.kisanbasket.freshatta.entity.product.CustomVariantEntity;
import com.kisanbasket.freshatta.entity.product.GrainComboEntity;
import com.kisanbasket.freshatta.entity.product.ProductVariantEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.CustomVariantRepository;
import com.kisanbasket.freshatta.repository.product.GrainComboRepository;
import com.kisanbasket.freshatta.repository.product.ProductVariantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomVariantservice {

    private final ModelMapper modelMapper;
    private final CustomVariantRepository customVariantRepository;
    private final GrainComboRepository grainComboRepository;
    private final ProductVariantRepository productVariantRepository;

    public CustomVariantservice(ModelMapper modelMapper, CustomVariantRepository customVariantRepository, GrainComboRepository grainComboRepository, ProductVariantRepository productVariantRepository) {
        this.modelMapper = modelMapper;
        this.customVariantRepository = customVariantRepository;
        this.grainComboRepository = grainComboRepository;
        this.productVariantRepository = productVariantRepository;
    }

    // create
    public CustomVariantDTO createCustomVariant(CustomVariantDTO customVariantDTO) {
        CustomVariantEntity customVariantEntity = modelMapper.map(customVariantDTO, CustomVariantEntity.class);

        GrainComboEntity grainComboEntity = grainComboRepository.findById(customVariantDTO.getGrainComboId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + customVariantDTO.getGrainComboId()));

        ProductVariantEntity productVariantEntity = productVariantRepository.findById(customVariantDTO.getProductVariantId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "ProductVariant not found with id: " + customVariantDTO.getProductVariantId()));

        customVariantEntity.setGrainComboEntity(grainComboEntity);
        customVariantEntity.setProductVariantEntity(productVariantEntity);

        CustomVariantEntity customVariantEntity1 = customVariantRepository.save(customVariantEntity);

        CustomVariantDTO customVariantDTO1 = modelMapper.map(customVariantEntity1, CustomVariantDTO.class);

        return customVariantDTO1;

    }

    // get-one
    public CustomVariantDTO getOneCustomVariant(Long customVarinatId) {
        CustomVariantEntity customVariantEntity = customVariantRepository.findById(customVarinatId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "CustomVariant not found with id: " + customVarinatId));

        CustomVariantDTO customVariantDTO = modelMapper.map(customVariantEntity, CustomVariantDTO.class);

        return customVariantDTO;
    }

    // get-all
    public List<CustomVariantDTO> getAllCustomVariant() {
        List<CustomVariantEntity> customVariantEntityList = customVariantRepository.findAll();

        List<CustomVariantDTO> customVariantDTOList = customVariantEntityList.stream()
                .map(customVariantEntity -> {
                    CustomVariantDTO customVariantDTO = modelMapper.map(customVariantEntity, CustomVariantDTO.class);
                    return customVariantDTO;
                }).toList();

        return customVariantDTOList;
    }

    // get-pageable
    public List<CustomVariantDTO> getPageableCustomVariant(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CustomVariantEntity> customVariantEntityPage = customVariantRepository.findAll(pageable);
        List<CustomVariantEntity> customVariantEntityList = customVariantEntityPage.getContent();

        List<CustomVariantDTO> customVariantDTOList = customVariantEntityList.stream()
                .map(customVariantEntity -> {
                    CustomVariantDTO customVariantDTO = modelMapper.map(customVariantEntity, CustomVariantDTO.class);
                    return customVariantDTO;
                }).toList();

        return customVariantDTOList;
    }

    // delete
    public void deleteCustomVariant(Long customVarinatId) {
        CustomVariantEntity customVariantEntity = customVariantRepository.findById(customVarinatId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "CustomVariant not found with id: " + customVarinatId));

        customVariantRepository.deleteById(customVarinatId);
    }

    // update
    public CustomVariantDTO updateCustomVariant(CustomVariantDTO customVariantDTO) {
        CustomVariantEntity customVariantEntity = customVariantRepository.findById(customVariantDTO.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "CustomVariant not found with id: " + customVariantDTO.getId()));

        if (customVariantDTO.getGrainComboId() != null) {
            GrainComboEntity grainComboEntity = grainComboRepository.findById(customVariantDTO.getGrainComboId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + customVariantDTO.getGrainComboId()));
            customVariantEntity.setGrainComboEntity(grainComboEntity);

        }

        if (customVariantDTO.getProductVariantId() != null) {
            ProductVariantEntity productVariantEntity = productVariantRepository.findById(customVariantDTO.getProductVariantId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "ProductVariant not found with id: " + customVariantDTO.getProductVariantId()));
            customVariantEntity.setProductVariantEntity(productVariantEntity);

        }

        CustomVariantEntity customVariantEntity1 = customVariantRepository.save(customVariantEntity);
        CustomVariantDTO customVariantDTO1 = modelMapper.map(customVariantEntity1, CustomVariantDTO.class);
        return customVariantDTO1;
    }
}
