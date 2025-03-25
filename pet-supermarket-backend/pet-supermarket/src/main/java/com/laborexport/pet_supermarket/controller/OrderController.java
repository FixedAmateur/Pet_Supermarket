package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;
import com.laborexport.pet_supermarket.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {
//    OrderResponse purchaseOrderByUserId(Long userId);
//
//    CustomPage<OrderResponse> getAllOrderByUserId(Long userId, Pageable pageable);
//
//
//    CustomPage<OrderResponse> getAllPurchasedOrderByUsedId(Long userId, boolean purchased, Pageable pageable);
//
//    OrderResponse getCartByUserId(Long userId);


    private final OrderService orderService;

    @Operation(summary = "purchase order by user id")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> purchaseOrder(@PathVariable("userId") Long id) {
        ApiResponse response = ApiResponse.succeed(orderService.purchaseOrderByUserId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get purchased orders by user id")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getPurchasedOrder(
            @PathVariable("userId") Long id,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderId", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse response = ApiResponse.succeed(orderService.getAllPurchasedOrderByUsedId(id, pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get cart by user id")
    @GetMapping("/{userId}/cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable("userId") Long id) {
        ApiResponse response = ApiResponse.succeed(orderService.getCartByUserId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
