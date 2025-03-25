package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.OrderProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderProductResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;
import com.laborexport.pet_supermarket.repository.OrderProductRepository;
import com.laborexport.pet_supermarket.service.OrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/order-products")
@RequiredArgsConstructor
@Tag(name = "Order product")
public class OrderProductController {
//    OrderProductResponse addProductToCartByUserId(Long userId, OrderProductRequest request);
//
//    MessageResponse removeProductFromCartByUserIdAndProductId(Long userId, Long productId);
//
//    OrderProductResponse updateCartProductByUserIdAndProductId(Long userId, Long productId, OrderProductRequest request);
//
//    OrderResponse updateCartProductDiscountByUserId(Long userId);

    private final OrderProductService orderProductService;

    @Operation(summary = "add product to user cart by user id and product id")
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable("userId") Long userId,
                                                 @PathVariable("productId") Long productId,
                                                 @RequestBody @Valid OrderProductRequest request) {
    ApiResponse response = ApiResponse.succeed(orderProductService.addProductToCartByUserId(userId, productId, request));
    return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "remove product from user cart by user id and product id")
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse> removeFromCart(
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId
    ) {
        ApiResponse response = ApiResponse.succeed(orderProductService.removeProductFromCartByUserIdAndProductId(userId, productId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
