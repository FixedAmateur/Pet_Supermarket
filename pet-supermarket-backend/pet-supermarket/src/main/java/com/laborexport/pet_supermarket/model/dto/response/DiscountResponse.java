package com.laborexport.pet_supermarket.model.dto.response;

import com.laborexport.pet_supermarket.model.entity.Image;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountResponse {
    private Long discountId;

    private Double discountPercentage;

    private Double discountMinus;

    private String discountDesc;

    private LocalDateTime discountExpiry;

    private LocalDateTime discountUpdated;

    private Set<ImageResponse> banners;
}
