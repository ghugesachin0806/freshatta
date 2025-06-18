package com.kisanbasket.freshatta.service.product;

import com.kisanbasket.freshatta.DTO.product.CarouselDTO;
import com.kisanbasket.freshatta.DTO.product.ImageDTO;
import com.kisanbasket.freshatta.entity.product.CarouselEntity;
import com.kisanbasket.freshatta.entity.product.ImageEntity;
import com.kisanbasket.freshatta.exception.CustomException;
import com.kisanbasket.freshatta.repository.product.CarouselRepository;
import com.kisanbasket.freshatta.repository.product.ImageRepository;
import com.kisanbasket.freshatta.utils.ImageUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
public class CarouselService {

    private final CarouselRepository carouselRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final ImageUtil imageUtil;

    @Value("${spring.carousel.image.customUrl}")
    private String customUrl;

    public CarouselService(CarouselRepository carouselRepository, ImageRepository imageRepository, ModelMapper modelMapper, ImageUtil imageUtil) {
        this.carouselRepository = carouselRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
        this.imageUtil = imageUtil;
    }

    public CarouselDTO createSingleCarousel(@Valid CarouselDTO carouselDTO, MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Image does not exist");
        }

        imageUtil.imageValidation(image);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setHasImage(true);
        imageEntity.setImageData(image.getBytes());
        imageEntity.setImageName(image.getName());
        imageEntity.setImageSize(image.getSize());
        imageEntity.setImageType(image.getContentType());
        ImageEntity imageEntity1 = imageRepository.save(imageEntity);

        CarouselEntity updatedCarouselEntity = null;

        Optional<CarouselEntity> carouselEntityOptional = carouselRepository.findByOrderId(carouselDTO.getOrderId());

        if (carouselEntityOptional.isPresent()) {
            CarouselEntity carouselEntity = carouselEntityOptional.get();
            imageRepository.deleteById(carouselEntity.getImageEntity().getId());
            carouselEntity.setImageEntity(imageEntity1);
            updatedCarouselEntity = carouselRepository.save(carouselEntity);
        } else {
            CarouselEntity carouselEntity = modelMapper.map(carouselDTO, CarouselEntity.class);
            carouselEntity.setImageEntity(imageEntity1);
            updatedCarouselEntity = carouselRepository.save(carouselEntity);
        }

        CarouselDTO updatedCarouselDTO = modelMapper.map(updatedCarouselEntity, CarouselDTO.class);
        updatedCarouselDTO.setImageUrl(imageUtil.createImageUrl(customUrl, updatedCarouselEntity.getImageEntity().getId()));

        return updatedCarouselDTO;
    }

    public List<CarouselDTO> getAllCarousel() {
        List<CarouselEntity> carouselEntityList = carouselRepository.findAll();

        List<CarouselDTO> carouselDTOList = carouselEntityList.stream()
                .map(carouselEntity -> {
                    CarouselDTO carouselDTO = modelMapper.map(carouselEntity, CarouselDTO.class);
                    carouselDTO.setImageUrl(imageUtil.createImageUrl(customUrl, carouselEntity.getImageEntity().getId()));
                    return carouselDTO;
                }).toList();

        return carouselDTOList;
    }

    public CarouselDTO getSingleCarousel(Long orderId) {

        Optional<CarouselEntity> carouselEntityOptional = carouselRepository.findByOrderId(orderId);

        if (carouselEntityOptional.isPresent()) {
            CarouselDTO carouselDTO = modelMapper.map(carouselEntityOptional.get(), CarouselDTO.class);
            carouselDTO.setImageUrl(imageUtil.createImageUrl(customUrl, carouselEntityOptional.get().getImageEntity().getId()));
            return carouselDTO;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "Carousel with order ID " + orderId + " not found");
        }
    }

    public void deleteCarousel(Long orderId) {
        Optional<CarouselEntity> carouselEntityOptional = carouselRepository.findByOrderId(orderId);

        if (carouselEntityOptional.isPresent()) {
            CarouselEntity carouselEntity = carouselEntityOptional.get();
            carouselRepository.deleteById(carouselEntity.getId());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "Image not found for order :" + orderId);
        }
    }

    public ImageDTO getImage(Long imageId) {
        ImageEntity imageEntity = imageRepository.findById(imageId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Image not found with id: " + imageId));
        return modelMapper.map(imageEntity, ImageDTO.class);
    }
}
