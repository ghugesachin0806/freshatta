package com.kisanbasket.freshatta.service.user;


import com.kisanbasket.freshatta.DTO.product.ProductDTO;
import com.kisanbasket.freshatta.entity.product.ImageEntity;
import com.kisanbasket.freshatta.entity.product.ProductEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.CustomVariantRepository;
import com.kisanbasket.freshatta.repository.product.ProductRepository;
import com.kisanbasket.freshatta.repository.product.ProductVariantRepository;
import com.kisanbasket.freshatta.utils.ImageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HomePageService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CustomVariantRepository customVariantRepository;
    private final ModelMapper modelMapper;
    private final ImageUtil imageUtil;

    @Value("${spring.product.image.customUrl}")
    private String customUrl;

    public HomePageService(ProductRepository productRepository, ProductVariantRepository productVariantRepository, CustomVariantRepository customVariantRepository, ModelMapper modelMapper, ImageUtil imageUtil) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.customVariantRepository = customVariantRepository;
        this.modelMapper = modelMapper;
        this.imageUtil = imageUtil;
    }


}
