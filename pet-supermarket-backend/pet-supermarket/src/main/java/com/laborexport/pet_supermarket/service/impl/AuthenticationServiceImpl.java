package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.model.dto.request.LoginRequest;
import com.laborexport.pet_supermarket.model.dto.request.RegisterRequest;
import com.laborexport.pet_supermarket.model.dto.response.JwtAuthResponse;
import com.laborexport.pet_supermarket.model.dto.response.UserResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.model.entity.Role;
import com.laborexport.pet_supermarket.model.entity.User;
import com.laborexport.pet_supermarket.repository.ImageRepository;
import com.laborexport.pet_supermarket.repository.RoleRepository;
import com.laborexport.pet_supermarket.repository.UserRepository;
import com.laborexport.pet_supermarket.service.AuthenticationService;
import com.laborexport.pet_supermarket.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    @Autowired
    private final ImageRepository imageRepository;
//    private final OrderRepository orderRepository;


    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "username", loginRequest.getUsername()));
        if (user == null) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            ));
        } catch (Exception exception) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }

        String jwtToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24);
        String refreshToken = jwtService.generateToken(user,jwtService.getExpirationTime()*24*2);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }

    @Override
    public JwtAuthResponse refreshToken(String refreshToken) {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        String accessToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24) ;
        refreshToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24*2);

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }



    @Override
    public UserResponse register(RegisterRequest registerRequest){
        if (userRepository.existsByUsername(registerRequest.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(
                ()->new ResourceNotFoundException("role", "role's name","ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);

        //set default avatar
        Image avatar = imageRepository.findById((long)1).orElseThrow(
                () -> new ResourceNotFoundException("image", "image id", 1));


        user.setAvatar(avatar);

        return modelMapper.map(userRepository.save(user), UserResponse.class);

//        Order order = new Order();
//        order.setUser(user);
//        orderRepository.save(order);

    }
}
