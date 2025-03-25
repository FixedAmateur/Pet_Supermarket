package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.ManufacturerRequest;
import com.laborexport.pet_supermarket.model.dto.request.PetRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.PetResponse;
import com.laborexport.pet_supermarket.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pet")
public class PetController {
    private final PetService petService;

//    PetResponse createPet(PetRequest request);
//
//    MessageResponse deletePetById(Long id);
//
//    PetResponse uploadPetImageByPetId(Long petId, ImageRequest request) throws IOException;
//
//    MessageResponse deletePetImageByPetIdAndImgId(Long petId, Long ImgId);
//
//    List<ImageResponse> getAllPetImagesByPetId(Long petId);

    @Operation(summary = "create pet")
    @PostMapping()
    public ResponseEntity<ApiResponse> createPet(@RequestBody @Valid PetRequest request) {
        ApiResponse response = ApiResponse.succeed(petService.createPet(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "delete pet by id")
    @DeleteMapping("/{petId}")
    public ResponseEntity<ApiResponse> deletePetByPetId(Long id) {
        ApiResponse response = ApiResponse.succeed(petService.deletePetById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "upload pet image by pet id")
    @PostMapping(path = "/{petId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadPetImage(@PathVariable("petId") Long id, @RequestPart("image") @Valid MultipartFile image) throws IOException {
        ImageRequest request = ImageRequest.builder().image(image).build();
        ApiResponse response = ApiResponse.succeed(petService.uploadPetImageByPetId(id, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get all pets")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllPets() {
        ApiResponse response = ApiResponse.succeed(petService.getAllPets());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







}
