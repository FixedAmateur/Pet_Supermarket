package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.UserRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;
    //private final OrderService orderService;

    @Operation(summary = "Get All User", description = "Get All User API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUser(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "fullName",required = false) String sortBy

    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).ascending() );
        ApiResponse apiResponse = ApiResponse.succeed(userService.getAllUser(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get User By Id", description = "Get User By Id API")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") Long userId){
        ApiResponse apiResponse = ApiResponse.succeed(userService.getUserById(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @Operation(summary = "Get Cart By User Id", description = "Get Cart By User Id API")
//    @GetMapping("/{userId}/cart")
//    public ResponseEntity<ApiResponse> getCartByUserId(@PathVariable("userId") Long userId){
//        ApiResponse apiResponse = ApiResponse.succeed(orderService.getCart(userId));
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }


    @Operation(summary = "Add User", description = "Add User API")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserRequest request){
        ApiResponse apiResponse = ApiResponse.succeed(userService.updateUser(userId, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update User Avatar By Id", description = "Update User Avatar By Id API")
    @PutMapping(path = "/{userId}/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> updateUserAvatar(@PathVariable("userId") Long userId,
                                                        @RequestPart("image") @Valid MultipartFile avatar) throws IOException {
        ApiResponse apiResponse = ApiResponse.succeed(userService.updateAvatarByUserId(userId, ImageRequest.builder().image(avatar).build()));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update User Password Id", description = "Update User Password By Id API")
    @PutMapping("/{userId}/change-password")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> updateUserPassword(@PathVariable("userId") Long userId,
                                                        @RequestParam @Valid String request) {
        ApiResponse apiResponse = ApiResponse.succeed(userService.updatePasswordByUserId(userId, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }







}
