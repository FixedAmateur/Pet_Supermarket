package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.DiscountRequest;
import com.laborexport.pet_supermarket.model.dto.response.*;
import com.laborexport.pet_supermarket.service.DiscountService;
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
@RequestMapping(path = "/api/discounts")
@RequiredArgsConstructor
@Tag(name = "Discount")
public class DiscountController {

    private final DiscountService discountService;

//    DiscountResponse createDiscount(DiscountRequest request);
//
//    MessageResponse removeDiscountByDiscountId(Long discountId);
//
//    CustomPage<DiscountResponse> getAllDiscount(Pageable pageable);
//
//    CustomPage<ProductResponse> getAllProductOnDiscountByDiscountId(Long discountId, Pageable pageable);

//    @Operation(summary = "Get All Book", description = "Get All Book API")
//    @GetMapping()
//    public ResponseEntity<ApiResponse> getAllBook(
//            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = "bookTitle", required = false) String sortBy
//    ) {
//        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
//        ApiResponse apiResponse = ApiResponse.success(bookService.getAllBook(pageable));
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
    @Operation(summary = "create discount")
    @PostMapping()
    public ResponseEntity<ApiResponse> createDiscount(@RequestBody @Valid DiscountRequest request) {
        ApiResponse response = ApiResponse.succeed(discountService.createDiscount(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "remove discount by discount id")
    @DeleteMapping("/{discountId}")
    public ResponseEntity<ApiResponse> removeDiscount(@PathVariable("discountId") Long id) {
        ApiResponse response = ApiResponse.succeed(discountService.removeDiscountByDiscountId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get all discounts")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDiscounts(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "discountId", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse response = ApiResponse.succeed(discountService.getAllDiscount(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "remove discount by discount id")
    @DeleteMapping("/{discountId}/products")
    public ResponseEntity<ApiResponse> getAllProductOnDiscount(
            @PathVariable("discountId") Long id,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "discountId", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse response = ApiResponse.succeed(discountService.getAllProductOnDiscountByDiscountId(id, pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
