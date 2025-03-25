package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.DiscountRequest;
import com.laborexport.pet_supermarket.model.dto.request.ManufacturerRequest;
import com.laborexport.pet_supermarket.model.dto.response.*;
import com.laborexport.pet_supermarket.service.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/manufacturers")
@RequiredArgsConstructor
@Tag(name = "Manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

//    ManufacturerResponse createManufacturer(ManufacturerRequest request);
//
//    CustomPage<ProductResponse> getAllProductsByManufacturerId(Long manufacturerId, Pageable pageable);
//
//    MessageResponse deleteManufacturerByManufacturerId(Long manufacturerId);

    @Operation(summary = "create manufacturer")
    @PostMapping()
    public ResponseEntity<ApiResponse> createManufacturer(@RequestBody @Valid ManufacturerRequest request) {
        ApiResponse response = ApiResponse.succeed(manufacturerService.createManufacturer(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get all product by manufacturer id")
    @GetMapping("/{manufacturerId}/products")
    public ResponseEntity<ApiResponse> getAllProductByManufacturerId(
            @PathVariable("manufacturerId") Long id,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "manufacturerId", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse response = ApiResponse.succeed(manufacturerService.getAllProductsByManufacturerId(id, pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "delete manufacturer by manufacturer id")
    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<ApiResponse> deleteManufacturerById(@PathVariable("manufacturerId") Long id) {
        ApiResponse response = ApiResponse.succeed(manufacturerService.deleteManufacturerByManufacturerId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
