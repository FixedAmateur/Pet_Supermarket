package com.laborexport.pet_supermarket.controller;

import com.cloudinary.Api;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.ProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

//    ProductResponse createProduct(ProductRequest request);
//
//    ProductResponse updateProductByProductId(Long productId, ProductRequest request);
//
//    MessageResponse removeProductByProductId(Long productId);
//
//    CustomPage<ProductResponse> getAllProducts(Pageable pageable);
//
//    ProductResponse addProductImageByProductId(Long productId, ImageRequest request) throws IOException;
//
//    MessageResponse deleteProductImageByProductIdAndImgId(Long productId, Long imgId);

    private final ProductService productService;

    @Operation(summary = "create product")
    @PostMapping()
    public ResponseEntity<ApiResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        ApiResponse response = ApiResponse.succeed(productService.createProduct(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "update product")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long id, @RequestBody @Valid ProductRequest request) {
        ApiResponse response = ApiResponse.succeed(productService.updateProductByProductId(id, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "remove product")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> removeProduct(@PathVariable("productId") Long id) {
        ApiResponse response = ApiResponse.succeed(productService.removeProductByProductId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get all products")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productName", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        ApiResponse response = ApiResponse.succeed(productService.getAllProducts(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "upload product image ")
    @PostMapping(path = "/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadProductImage(@PathVariable("productId") Long id, @RequestPart("image") MultipartFile image) throws IOException {
        ImageRequest request = ImageRequest.builder().image(image).build();
        ApiResponse response = ApiResponse.succeed(productService.addProductImageByProductId(id, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "remove product")
    @DeleteMapping("/{productId}/{imgId}")
    public ResponseEntity<ApiResponse> removeProductImage(@PathVariable("productId") Long productId, @PathVariable("imgId") Long imgId) {
        ApiResponse response = ApiResponse.succeed(productService.deleteProductImageByProductIdAndImgId(productId, imgId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
