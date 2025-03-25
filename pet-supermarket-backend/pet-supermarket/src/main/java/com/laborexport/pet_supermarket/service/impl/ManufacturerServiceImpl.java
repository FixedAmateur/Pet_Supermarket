package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.ManufacturerRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.ManufacturerResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.model.entity.Manufacturer;
import com.laborexport.pet_supermarket.model.entity.Product;
import com.laborexport.pet_supermarket.repository.ManufacturerRepository;
import com.laborexport.pet_supermarket.repository.ProductRepository;
import com.laborexport.pet_supermarket.service.DiscountService;
import com.laborexport.pet_supermarket.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public ManufacturerResponse createManufacturer(ManufacturerRequest request) {
        Manufacturer manufacturer = mapper.map(request, Manufacturer.class);

        return mapper.map(manufacturerRepository.save(manufacturer), ManufacturerResponse.class);
    }

    @Override
    public CustomPage<ProductResponse> getAllProductsByManufacturerId(Long manufacturerId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByManufacturerId(manufacturerId, pageable);


        return CustomPage.<ProductResponse>builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .totalPages(products.getTotalPages())
                .pageContent(products.getContent().stream().
                        map(product -> mapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList()))
                .build();

    }

    @Override
    public MessageResponse deleteManufacturerByManufacturerId(Long manufacturerId) {
        if (!manufacturerRepository.existsById(manufacturerId)) throw new ResourceNotFoundException("manufacturerId", "manufacturer", manufacturerId);
        manufacturerRepository.deleteById(manufacturerId);
        return new MessageResponse("Manufacturer with the id " + manufacturerId + " is deleted successfully");
    }
}
