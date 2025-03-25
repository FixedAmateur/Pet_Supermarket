package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.ManufacturerRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.ManufacturerResponse;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ManufacturerService {
    ManufacturerResponse createManufacturer(ManufacturerRequest request);

    CustomPage<ProductResponse> getAllProductsByManufacturerId(Long manufacturerId, Pageable pageable);

    MessageResponse deleteManufacturerByManufacturerId(Long manufacturerId);

}
