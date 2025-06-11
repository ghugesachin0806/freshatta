package com.kisanbasket.freshatta.service.product;

import com.kisanbasket.freshatta.DTO.ImageDTO;
import com.kisanbasket.freshatta.DTO.ProductDTO;
import com.kisanbasket.freshatta.entity.product.ImageEntity;
import com.kisanbasket.freshatta.entity.product.ProductEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.ImageRepository;
import com.kisanbasket.freshatta.repository.product.ProductRepository;
import com.kisanbasket.freshatta.utils.ImageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final ImageUtil imageUtil;

    @Value("${spring.product.image.customUrl}")
    private String customUrl;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository, ModelMapper modelMapper, ImageUtil imageUtil) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
        this.imageUtil = imageUtil;
    }

    // create
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile[] images) throws IOException {

        imageUtil.imageListValidation(images);

        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        ProductEntity updatedProductEntity = productRepository.save(productEntity);
        ProductDTO productDTO1 = modelMapper.map(updatedProductEntity, ProductDTO.class);

        List<String> imageList = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) continue;

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setHasImage(true);
            imageEntity.setImageData(image.getBytes());
            imageEntity.setImageName(image.getName());
            imageEntity.setImageSize(image.getSize());
            imageEntity.setImageType(image.getContentType());
            imageEntity.setProductEntity(updatedProductEntity);
            ImageEntity imageEntity1 = imageRepository.save(imageEntity);
            imageList.add(imageUtil.createImageUrl(customUrl, imageEntity1.getId()));
        }
        productDTO1.setImageEntityStringList(imageList);
        return productDTO1;
    }

    // getone
    public ProductDTO getOneProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));

        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        List<String> imageList = new ArrayList<>();

        for (ImageEntity image : productEntity.getImageEntityList()) {
            imageList.add(imageUtil.createImageUrl(customUrl, image.getId()));
        }
        productDTO.setImageEntityStringList(imageList);

        return productDTO;
    }


    // getall
    public List<ProductDTO> getAllProduct() {

        List<ProductEntity> productEntityList = productRepository.findAll();
        return productEntityList.stream()
                .map(productEntity -> {
                    ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);

                    List<String> imageList = new ArrayList<>();

                    for (ImageEntity image : productEntity.getImageEntityList()) {
                        imageList.add(imageUtil.createImageUrl(customUrl, image.getId()));
                    }
                    productDTO.setImageEntityStringList(imageList);
                    return productDTO;

                }).toList();
    }

    // getpageable
    public List<ProductDTO> getPageableProduct(int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductEntity> productEntityPage = productRepository.findAll(pageable);

        List<ProductEntity> productEntityList = productEntityPage.getContent();

        return productEntityList.stream()
                .map(productEntity -> {
                    ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);

                    List<String> imageList = new ArrayList<>();

                    for (ImageEntity image : productEntity.getImageEntityList()) {
                        imageList.add(imageUtil.createImageUrl(customUrl, image.getId()));
                    }
                    productDTO.setImageEntityStringList(imageList);
                    return productDTO;

                }).toList();
    }


    // delete
    public void deleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));

        productRepository.deleteById(productId);
    }

    // update
    public ProductDTO updateproduct(ProductDTO productDTO, MultipartFile[] images) throws IOException {

        ProductEntity productEntity = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found with id: " + productDTO.getId()));

        productEntity.setName(productDTO.getName() != null ? productDTO.getName() : productEntity.getName());
        productEntity.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : productEntity.getDescription());

        ProductEntity updatedProductEntity = productRepository.save(productEntity);
        ProductDTO productDTO1 = modelMapper.map(updatedProductEntity, ProductDTO.class);

        if (images != null && images.length > 0) {

            imageUtil.imageListValidation(images);
            List<ImageEntity> preImageList = productEntity.getImageEntityList();

            for (ImageEntity imageEntity : preImageList) {
                imageRepository.deleteById(imageEntity.getId());
            }

            List<String> imageList = new ArrayList<>();

            for (MultipartFile image : images) {
                if (image == null || image.isEmpty()) continue;

                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setHasImage(true);
                imageEntity.setImageData(image.getBytes());
                imageEntity.setImageName(image.getName());
                imageEntity.setImageSize(image.getSize());
                imageEntity.setImageType(image.getContentType());
                imageEntity.setProductEntity(updatedProductEntity);
                ImageEntity imageEntity1 = imageRepository.save(imageEntity);
                imageList.add(imageUtil.createImageUrl(customUrl, imageEntity1.getId()));
            }

            productDTO1.setImageEntityStringList(imageList);
        }

        if (productDTO1.getImageEntityStringList() == null) {
            List<String> imageList = new ArrayList<>();

            List<ImageEntity> preImageList = productEntity.getImageEntityList();

            for (ImageEntity imageEntity : preImageList) {
                imageList.add(imageUtil.createImageUrl(customUrl, imageEntity.getId()));
            }
            productDTO1.setImageEntityStringList(imageList);
        }

        return productDTO1;
    }

    // get image
    public ImageDTO getImage(Long imageId) {
        ImageEntity imageEntity = imageRepository.findById(imageId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Image not found with id: " + imageId));
        return modelMapper.map(imageEntity, ImageDTO.class);
    }
}
