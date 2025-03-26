package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.ProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.model.entity.Product;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProductByProductId(Long productId, ProductRequest request);

    MessageResponse removeProductByProductId(Long productId);

    CustomPage<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse addProductImageByProductId(Long productId, ImageRequest request) throws IOException;

    MessageResponse deleteProductImageByProductIdAndImgId(Long productId, Long imgId);


    ProductResponse addPetToProduct(Long petId, Long productId);

    CustomPage<ProductResponse> getFavoritesProductByUserId(Long userId, Pageable pageable);

    MessageResponse addProductToFavorites(Long userId, Long productId);

    MessageResponse removeProductFromFavorite(Long userId, Long productId);
}
