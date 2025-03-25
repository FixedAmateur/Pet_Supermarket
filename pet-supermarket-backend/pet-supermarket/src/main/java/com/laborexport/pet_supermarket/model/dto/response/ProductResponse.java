package com.laborexport.pet_supermarket.model.dto.response;

import com.laborexport.pet_supermarket.model.entity.Image;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long productId;

    private String productType;

    private String productName;

    private String productDesc;

    private Double unitPrice;

    private Set<DiscountResponse> discounts;

    private Set<ImageResponse> prodImages;
}
