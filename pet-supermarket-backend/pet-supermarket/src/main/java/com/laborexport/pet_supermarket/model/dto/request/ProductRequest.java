package com.laborexport.pet_supermarket.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String productType;

    private String productName;

    private String productDesc;

    private Double unitPrice;
}
