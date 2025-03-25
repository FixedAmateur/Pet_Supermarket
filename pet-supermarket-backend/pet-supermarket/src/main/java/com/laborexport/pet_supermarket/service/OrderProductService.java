package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.OrderProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderProductResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;

public interface OrderProductService {

    OrderProductResponse addProductToCartByUserId(Long userId, Long productId, OrderProductRequest request);

    MessageResponse removeProductFromCartByUserIdAndProductId(Long userId, Long productId);

    OrderProductResponse updateCartProductByUserIdAndProductId(Long userId, Long productId, OrderProductRequest request);

    OrderResponse updateCartProductDiscountByUserId(Long userId);
}
