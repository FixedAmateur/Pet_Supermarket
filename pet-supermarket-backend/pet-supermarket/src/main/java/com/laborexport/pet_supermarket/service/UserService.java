package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.UserRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {
    UserResponse getUserById(Long userId);

    CustomPage<UserResponse> getAllUser(Pageable pageable);

    UserResponse updateUser(Long userId, UserRequest request);

    UserResponse updateAvatarByUserId(Long id, ImageRequest request) throws IOException;

    MessageResponse updatePasswordByUserId(Long id, String updatedPassword);


}
