package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.LoginRequest;
import com.laborexport.pet_supermarket.model.dto.request.RegisterRequest;
import com.laborexport.pet_supermarket.model.dto.response.JwtAuthResponse;
import com.laborexport.pet_supermarket.model.dto.response.UserResponse;

public interface AuthenticationService {
    JwtAuthResponse login(LoginRequest request);

    JwtAuthResponse refreshToken(String refreshToken);

    UserResponse register(RegisterRequest request);

}
