package com.laborexport.pet_supermarket.model.dto.request;


import com.laborexport.pet_supermarket.model.entity.OrderProduct;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Double totalPrice;

    private Long quantity;

    private Set<OrderProductRequest> orderProducts;
}
