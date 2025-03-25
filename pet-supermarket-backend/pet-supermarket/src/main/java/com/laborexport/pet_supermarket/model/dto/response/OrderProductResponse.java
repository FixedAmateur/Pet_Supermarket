package com.laborexport.pet_supermarket.model.dto.response;

import com.laborexport.pet_supermarket.model.entity.Discount;
import com.laborexport.pet_supermarket.model.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponse {
    private Long orderProductId;

    private Long subQuantity;

    private Double subPrice;

    private ProductResponse product;

    private Discount appliedDiscount;
}
