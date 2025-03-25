package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.constants.ImageConstants;
import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.UserRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.UserResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.model.entity.User;
import com.laborexport.pet_supermarket.repository.UserRepository;
import com.laborexport.pet_supermarket.service.ImageService;
import com.laborexport.pet_supermarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return  modelMapper.map(user, UserResponse.class);
    }

    @Override
    public CustomPage<UserResponse> getAllUser(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return CustomPage.<UserResponse>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageContent(page.getContent().stream().map(user->modelMapper.map(user, UserResponse.class)).toList())
                .build();

    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!request.getUsername().equals(user.getUsername())){
            throw new AppException(ErrorCode.USERNAME_CAN_NOT_BE_CHANGED);
        }
        modelMapper.map(request,user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public UserResponse updateAvatarByUserId(Long id, ImageRequest request) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        Long avatarImgId = user.getAvatar().getImgId();
        Image updatedAvatar = new Image();
        if (avatarImgId == ImageConstants.DEFAULT_AVATAR_IMG_ID) {
            updatedAvatar = modelMapper.map(imageService.uploadImage(request), Image.class);
        } else  updatedAvatar = modelMapper.map(imageService.updateImageByImageId(avatarImgId, request), Image.class);
        user.setAvatar(updatedAvatar);
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public MessageResponse updatePasswordByUserId(Long id, String updatedPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setPassword(passwordEncoder.encode(updatedPassword));
        userRepository.save(user);
        return new MessageResponse("User with the username + " + user.getUsername() + " + has his/her password updated successfully!");
    }
}
