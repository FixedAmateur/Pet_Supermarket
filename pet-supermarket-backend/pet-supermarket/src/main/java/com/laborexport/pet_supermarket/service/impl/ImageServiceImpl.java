package com.laborexport.pet_supermarket.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.repository.ImageRepository;
import com.laborexport.pet_supermarket.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    private final ImageRepository imageRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final Cloudinary cloudinary;

    @Override
    public ImageResponse uploadImage(ImageRequest request) throws IOException {
        Image image = new Image();
        image.setImgUrl(generateMediaLink(request.getImage()));
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
        image.setImgUrl(generateMediaLink(request.getImage()));

        return modelMapper.map(imageRepository.save(image), ImageResponse.class);
    }

    @Override
    public MessageResponse deleteImageByImageId(Long id) {
        if (!imageRepository.existsById(id)) throw new ResourceNotFoundException("image", "image id", id);
        imageRepository.deleteById(id);
        return new MessageResponse("Image with the id " + " deleted successfully!");
    }




    // generate media link function
    public String generateMediaLink(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        String publicValue = generatePublicValue(file.getOriginalFilename());
        String extension = getFileName(file.getOriginalFilename())[1];
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw new IllegalArgumentException("Original filename must not be null");
        }
        // Possible new validation
        if (cloudinary.config.apiKey == null || cloudinary.config.apiKey.isEmpty()) {
            throw new IllegalArgumentException("Must supply api_key"); // This line is the culprit
        }
        File fileUpload = convert(file);
        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));
        cleanDisk(fileUpload);
        return cloudinary.url().generate(StringUtils.join(publicValue,".",extension));
    }

    public File convert(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        File convFile = new File(StringUtils.join(generatePublicValue(file.getOriginalFilename()), getFileName(file.getOriginalFilename())[1]));
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;
    }

    public String generatePublicValue(String originalName) {
        String fileName = getFileName(originalName)[0];
        return StringUtils.join(UUID.randomUUID().toString(),"-",fileName);
    }

    public String[] getFileName(String originalName) {
        return originalName.split("\\.");
    }

    public void cleanDisk(File file) {
        try {
            Path filePath = file.toPath();
            Files.delete(filePath);
        } catch (IOException e) {
            System.err.print("Error");
        }
    }
}
