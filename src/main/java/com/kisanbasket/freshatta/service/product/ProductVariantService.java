package com.kisanbasket.freshatta.service.product;

import com.kisanbasket.freshatta.DTO.product.ProductVariantDTO;
import com.kisanbasket.freshatta.entity.product.GrainComboEntity;
import com.kisanbasket.freshatta.entity.product.ProductEntity;
import com.kisanbasket.freshatta.entity.product.ProductVariantEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.GrainComboRepository;
import com.kisanbasket.freshatta.repository.product.ProductRepository;
import com.kisanbasket.freshatta.repository.product.ProductVariantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final GrainComboRepository grainComboRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductVariantService(ProductVariantRepository productVariantRepository, GrainComboRepository grainComboRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.productVariantRepository = productVariantRepository;
        this.grainComboRepository = grainComboRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //create
    public ProductVariantDTO createProductVariant(ProductVariantDTO productVariantDTO) {
        ProductVariantEntity productVariantEntity = modelMapper.map(productVariantDTO, ProductVariantEntity.class);

        GrainComboEntity grainComboEntity = grainComboRepository.findById(productVariantDTO.getGrainComboId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + productVariantDTO.getGrainComboId()));
        productVariantEntity.setGrainComboEntity(grainComboEntity);

        ProductEntity productEntity = productRepository.findById(productVariantDTO.getProductId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found with id: " + productVariantDTO.getProductId()));
        productVariantEntity.setProductEntity(productEntity);

        ProductVariantEntity productVariantEntity1 = productVariantRepository.save(productVariantEntity);
        ProductVariantDTO productVariantDTO1 = modelMapper.map(productVariantEntity1, ProductVariantDTO.class);
        return productVariantDTO1;
    }

    // get-one
    public ProductVariantDTO getProductVariant(Long productVariantId) {
        ProductVariantEntity productVariantEntity = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "ProductVariant not found with id: " + productVariantId));

        ProductVariantDTO productVariantDTO = modelMapper.map(productVariantEntity, ProductVariantDTO.class);

        return productVariantDTO;
    }

    // get-all
    public List<ProductVariantDTO> getAllProductvariantList() {
        List<ProductVariantEntity> productVariantEntityList = productVariantRepository.findAll();

        List<ProductVariantDTO> productVariantDTOList = productVariantEntityList.stream()
                .map(productVariantEntity -> {
                    ProductVariantDTO productVariantDTO = modelMapper.map(productVariantEntity, ProductVariantDTO.class);
                    return productVariantDTO;
                }).toList();

        return productVariantDTOList;
    }

    // get-pageable
    public List<ProductVariantDTO> getAllProductVariantPageable(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductVariantEntity> productEntityPage = productVariantRepository.findAll(pageable);

        List<ProductVariantEntity> productVariantEntityList = productEntityPage.getContent();

        List<ProductVariantDTO> productVariantDTOList = productVariantEntityList.stream()
                .map(productVariantEntity -> {
                    ProductVariantDTO productVariantDTO = modelMapper.map(productVariantEntity, ProductVariantDTO.class);
                    return productVariantDTO;
                }).toList();

        return productVariantDTOList;
    }

    // delete
    public void deleteProductVariant(Long productVariantId) {
        ProductVariantEntity productVariantEntity = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "ProductVariant not found with id: " + productVariantId));

        productVariantRepository.deleteById(productVariantId);
    }

    // update
    public ProductVariantDTO updateProductVariant(ProductVariantDTO productVariantDTO) {
        ProductVariantEntity productVariantEntity = productVariantRepository.findById(productVariantDTO.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "ProductVariant not found with id: " + productVariantDTO.getId()));

        productVariantEntity.setName(productVariantDTO.getName() != null ? productVariantDTO.getName() : productVariantEntity.getName());
        productVariantEntity.setDescription(productVariantDTO.getDescription() != null ? productVariantDTO.getDescription() : productVariantEntity.getDescription());
        productVariantEntity.setWeight(productVariantDTO.getWeight() != null ? productVariantDTO.getWeight() : productVariantEntity.getWeight());
        productVariantEntity.setCustomMix(productVariantDTO.getCustomMix() != null ? productVariantDTO.getCustomMix() : productVariantEntity.getCustomMix());

        if (productVariantDTO.getGrainComboId() != null) {
            GrainComboEntity grainComboEntity = grainComboRepository.findById(productVariantDTO.getGrainComboId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "GrainCombo not found with id: " + productVariantDTO.getGrainComboId()));

            productVariantEntity.setGrainComboEntity(grainComboEntity);
        }

        if (productVariantDTO.getProductId() != null) {
            ProductEntity productEntity = productRepository.findById(productVariantDTO.getProductId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found with id: " + productVariantDTO.getProductId()));

            productVariantEntity.setProductEntity(productEntity);
        }

        ProductVariantEntity productVariantEntity1 = productVariantRepository.save(productVariantEntity);
        ProductVariantDTO productVariantDTO1 = modelMapper.map(productVariantEntity1, ProductVariantDTO.class);

        return productVariantDTO1;
    }
}
