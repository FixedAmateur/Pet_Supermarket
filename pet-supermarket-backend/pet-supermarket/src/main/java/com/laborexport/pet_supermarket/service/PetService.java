package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.PetRequest;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.PetResponse;

import java.io.IOException;
import java.util.List;

public interface PetService {
    PetResponse createPet(PetRequest request);

    MessageResponse deletePetById(Long id);

    PetResponse uploadPetImageByPetId(Long petId, ImageRequest request) throws IOException;

    MessageResponse deletePetImageByPetIdAndImgId(Long petId, Long ImgId);

    List<ImageResponse> getAllPetImagesByPetId(Long petId);

    PetResponse getPetByPetId(Long id);

    List<PetResponse> getAllPets();

}
