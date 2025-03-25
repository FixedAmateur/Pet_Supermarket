package com.laborexport.pet_supermarket.model.dto.response;

import com.laborexport.pet_supermarket.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;

    private Double totalPrice;

    private Long quantity;

    private boolean purchased;

    private LocalDateTime orderUpdated;

    private UserResponse user;

    private Set<OrderProductResponse> orderProducts;
}
