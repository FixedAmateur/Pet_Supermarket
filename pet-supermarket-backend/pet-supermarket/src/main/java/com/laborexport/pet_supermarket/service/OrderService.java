package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;
import com.laborexport.pet_supermarket.model.entity.Order;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse purchaseOrderByUserId(Long userId);

    CustomPage<OrderResponse> getAllOrderByUserId(Long userId, Pageable pageable);


    CustomPage<OrderResponse> getAllPurchasedOrderByUsedId(Long userId, Pageable pageable);

    OrderResponse getCartByUserId(Long userId);

}
