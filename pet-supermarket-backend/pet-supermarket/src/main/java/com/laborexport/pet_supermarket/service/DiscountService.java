package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.DiscountRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.DiscountResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.model.entity.Discount;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountResponse createDiscount(DiscountRequest request);

    MessageResponse removeDiscountByDiscountId(Long discountId);

    CustomPage<DiscountResponse> getAllDiscount(Pageable pageable);

    CustomPage<ProductResponse> getAllProductOnDiscountByDiscountId(Long discountId, Pageable pageable);
}
