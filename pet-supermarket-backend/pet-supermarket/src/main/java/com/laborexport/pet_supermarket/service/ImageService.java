package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;

import java.io.IOException;

public interface ImageService {
    public ImageResponse uploadImage(ImageRequest request) throws IOException;

    public ImageResponse getImageByImageId(Long id);

    public ImageResponse updateImageByImageId(Long id, ImageRequest request) throws IOException;

    public MessageResponse deleteImageByImageId(Long id);

}
