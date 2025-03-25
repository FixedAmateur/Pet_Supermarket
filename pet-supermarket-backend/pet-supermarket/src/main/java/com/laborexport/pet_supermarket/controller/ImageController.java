package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/images")
@RequiredArgsConstructor
@Tag(name = "Image", description = "Image API")
public class ImageController {
    private final ImageService imageService;

    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadImage(@RequestPart("image") @Valid MultipartFile image) throws IOException {
        ImageRequest request = ImageRequest.builder().image(image).build();
        ApiResponse apiResponse = ApiResponse.succeed(imageService.uploadImage(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ApiResponse> getImageByImageId(@PathVariable("imageId") Long id) {
        ApiResponse apiResponse = ApiResponse.succeed(imageService.getImageByImageId(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
