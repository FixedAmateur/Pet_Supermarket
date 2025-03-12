package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.repository.ImageRepository;
import com.laborexport.pet_supermarket.service.ImageService;
import com.laborexport.pet_supermarket.util.CloudinaryUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public ImageResponse uploadImage(ImageRequest request) throws IOException {
        Image image = new Image();
        image.setImgUrl(CloudinaryUtils.generateMediaLink(request.getImage()));
        return modelMapper.map(imageRepository.save(image), ImageResponse.class);
    }

    @Override
    public ImageResponse getImageByImageId(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("image", "image id", id)
        );

        return modelMapper.map(image, ImageResponse.class);
    }

    @Override
    public ImageResponse updateImageByImageId(Long id, ImageRequest request) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("image", "image id", id)
        );
        image.setImgUrl(CloudinaryUtils.generateMediaLink(request.getImage()));

        return modelMapper.map(imageRepository.save(image), ImageResponse.class);
    }

    @Override
    public String deleteImageByImageId(Long id) {
        if (!imageRepository.existsById(id)) throw new ResourceNotFoundException("image", "image id", id);
        imageRepository.deleteById(id);
        return "Image with the id " + " deleted successfully!";
    }
}
