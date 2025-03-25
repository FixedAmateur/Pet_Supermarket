package com.laborexport.pet_supermarket.model.dto.request;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class DiscountRequest {
    private Double discountPercentage;

    private Double discountMinus;

    private String discountDesc;

    private LocalDateTime discountExpiry;

    private LocalDateTime discountUpdated;

    private Set<ImageRequest> banners;

    public DiscountRequest() {
        if (discountPercentage != 1 && discountMinus != 0) throw new AppException(ErrorCode.ONLY_ONE_DISCOUNT_TYPE_PERMITTED);

    }

    public DiscountRequest(Double discountPercentage, Double discountMinus, String discountDesc, LocalDateTime discountExpiry, LocalDateTime discountUpdated, Set<ImageRequest> banners) {
        this.discountPercentage = discountPercentage;
        this.discountMinus = discountMinus;
        this.discountDesc = discountDesc;
        this.discountExpiry = discountExpiry;
        this.discountUpdated = discountUpdated;
        this.banners = banners;
        if (discountPercentage != 1 && discountMinus != 0) throw new AppException(ErrorCode.ONLY_ONE_DISCOUNT_TYPE_PERMITTED);
    }
}
