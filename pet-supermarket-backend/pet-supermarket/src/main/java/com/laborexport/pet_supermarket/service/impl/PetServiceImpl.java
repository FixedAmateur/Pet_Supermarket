package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.constants.ImageConstants;
import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.PetRequest;
import com.laborexport.pet_supermarket.model.dto.response.ImageResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.PetResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.model.entity.Pet;
import com.laborexport.pet_supermarket.repository.PetRepository;
import com.laborexport.pet_supermarket.service.ImageService;
import com.laborexport.pet_supermarket.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private final ImageService imageService;

    private final ModelMapper mapper;

    @Override
    public PetResponse createPet(PetRequest request) {
        Pet pet = mapper.map(request, Pet.class);
        Image defaultPetImage = mapper.map(imageService.getImageByImageId(ImageConstants.DEFAULT_PET_IMAGE_ID), Image.class);

        Set<Image> petImages = new HashSet<>();
        petImages.add(defaultPetImage);
        pet.setPetImages(petImages);

        return mapper.map(petRepository.save(pet), PetResponse.class);
    }

    @Override
    public MessageResponse deletePetById(Long id) {
        if (!petRepository.existsById(id)) throw new ResourceNotFoundException("pet id", "pet", id);
        petRepository.deleteById(id);
        return new MessageResponse("pet with the id " + id + " got deleted successfully.");
    }

    @Override
    public PetResponse uploadPetImageByPetId(Long petId, ImageRequest request) throws IOException {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", petId));
        Image uploadedImage = mapper.map(imageService.uploadImage(request), Image.class);
        Set<Image> images = pet.getPetImages();
        images.add(uploadedImage);
        pet.setPetImages(images);
        return mapper.map(petRepository.save(pet), PetResponse.class);
    }

    @Override
    public MessageResponse deletePetImageByPetIdAndImgId(Long petId, Long imgId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", petId));
        Set<Image> images = pet.getPetImages();
        if (imgId == ImageConstants.DEFAULT_PET_IMAGE_ID) throw new AppException(ErrorCode.CANNOT_REMOVE_DEFAULT_IMAGE);

        boolean found = false;
        for (Image image : images) if (image.getImgId() == petId) {
                found = true; break;
        }
        if (!found) throw new AppException(ErrorCode.NOT_EXIST_ANY_IMAGE_WITHIN_PET);

        imageService.deleteImageByImageId(imgId);
        return new MessageResponse("Pet with the pet id " + petId + " has its image with the image id " + imgId + " is deleted successfully.");

    }

    @Override
    public List<ImageResponse> getAllPetImagesByPetId(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", petId));
        Set<Image> images = pet.getPetImages();
        return images.stream()
                .map(image -> mapper.map(image, ImageResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public PetResponse getPetByPetId(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", id));

        return mapper.map(pet, PetResponse.class);
    }

    @Override
    public List<PetResponse> getAllPets() {
        List<Pet> pets = petRepository.findAll();

        return pets.stream().map(pet -> mapper.map(pet, PetResponse.class)).collect(Collectors.toList());
    }
}
