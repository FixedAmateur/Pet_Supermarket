package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.DiscountRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.DiscountResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.model.entity.Discount;
import com.laborexport.pet_supermarket.model.entity.Product;
import com.laborexport.pet_supermarket.repository.DiscountRepository;
import com.laborexport.pet_supermarket.repository.ProductRepository;
import com.laborexport.pet_supermarket.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private final DiscountRepository discountRepository;

    @Autowired
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public DiscountResponse createDiscount(DiscountRequest request) {
        Discount discount = mapper.map(request, Discount.class);
        if (discount.getDiscountPercentage() != 1
                && discount.getDiscountMinus() != 0)
            throw new AppException(ErrorCode.ONLY_ONE_DISCOUNT_TYPE_PERMITTED);



        return mapper.map(discountRepository.save(discount), DiscountResponse.class);
    }

    @Override
    public MessageResponse removeDiscountByDiscountId(Long discountId) {
        if (!discountRepository.existsById(discountId)) throw new ResourceNotFoundException("discount id", "discount", discountId);
        discountRepository.deleteById(discountId);
        return new MessageResponse("Discount with the id " + discountId + " is deleted successfully");
    }

    @Override
    public CustomPage<DiscountResponse> getAllDiscount(Pageable pageable) {
        Page<Discount> discounts = discountRepository.findAll(pageable);
        return CustomPage.<DiscountResponse>builder()
                .pageNo(discounts.getNumber() + 1)
                .pageSize(discounts.getSize())
                .pageContent(discounts.getContent().stream()
                        .map(discount -> mapper.map(discount, DiscountResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CustomPage<ProductResponse> getAllProductOnDiscountByDiscountId(Long discountId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByDiscountId(discountId, pageable);
        return CustomPage.<ProductResponse>builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .pageContent(products.getContent().stream()
                        .map(product -> mapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }
}
