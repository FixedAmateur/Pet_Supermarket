package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.constants.ImageConstants;
import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.ImageRequest;
import com.laborexport.pet_supermarket.model.dto.request.ProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.PetResponse;
import com.laborexport.pet_supermarket.model.dto.response.ProductResponse;
import com.laborexport.pet_supermarket.model.entity.Image;
import com.laborexport.pet_supermarket.model.entity.Pet;
import com.laborexport.pet_supermarket.model.entity.Product;
import com.laborexport.pet_supermarket.model.entity.User;
import com.laborexport.pet_supermarket.repository.ImageRepository;
import com.laborexport.pet_supermarket.repository.PetRepository;
import com.laborexport.pet_supermarket.repository.ProductRepository;
import com.laborexport.pet_supermarket.repository.UserRepository;
import com.laborexport.pet_supermarket.service.ImageService;
import com.laborexport.pet_supermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ImageService imageService;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper mapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByProductNameAndProductType(request.getProductName(), request.getProductType()))
            throw new AppException(ErrorCode.PRODUCT_NAME_AND_TYPE_ALREADY_EXIST);
        Image defaultProductImage = mapper.map(imageService.getImageByImageId(ImageConstants.DEFAULT_PRODUCT_IMAGE_ID), Image.class);
        Image productImage = new Image();
        defaultProductImage.setImgId(productImage.getImgId());
        imageRepository.save(defaultProductImage);

        Set<Image> prodImages = new HashSet<>();
        prodImages.add(defaultProductImage);


        Product product = mapper.map(request, Product.class);
        product.setProdImages(prodImages);
        return mapper.map(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public ProductResponse updateProductByProductId(Long productId, ProductRequest request) {
        if (!productRepository.existsById(productId)) throw new ResourceNotFoundException("product id", "product", productId);
        if (!productRepository.existsByProductNameAndProductType(request.getProductName(), request.getProductType()))
            throw new AppException(ErrorCode.CANNOT_ALTER_NOT_EXISTED_PRODUCT);


        Product updatedProduct = mapper.map(request, Product.class);
        updatedProduct.setProductId(productId);
        return mapper.map(productRepository.save(updatedProduct), ProductResponse.class);
    }

    @Override
    public MessageResponse removeProductByProductId(Long productId) {
        if (!productRepository.existsById(productId)) throw new ResourceNotFoundException("product id", "product", productId);
        productRepository.deleteById(productId);
        return new MessageResponse("product with the id " + productId + " got deleted successfully.");
    }

    @Override
    public CustomPage<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return CustomPage.<ProductResponse>builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize() + 1)
                .pageContent(products.stream()
                        .map(product -> mapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList()))
                .build();

    }

    @Override
    public ProductResponse addProductImageByProductId(Long productId, ImageRequest request) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product id", "product", productId));
        Image uploadImage = mapper.map(imageService.uploadImage(request), Image.class);

        Set<Image> prodImages = product.getProdImages();
        prodImages.add(uploadImage);
        product.setProdImages(prodImages);

        return mapper.map(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public MessageResponse deleteProductImageByProductIdAndImgId(Long productId, Long imgId) {
//        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", petId));
//        Set<Image> images = pet.getPetImages();
//        if (imgId == ImageConstants.DEFAULT_PET_IMAGE_ID) throw new AppException(ErrorCode.CANNOT_REMOVE_DEFAULT_PET_IMAGE);
//
//        boolean found = false;
//        for (Image image : images) if (image.getImgId() == petId) {
//            found = true; break;
//        }
//        if (!found) throw new AppException(ErrorCode.NOT_EXIST_ANY_IMAGE_WITHIN_PET);
//
//        imageService.deleteImageByImageId(imgId);
//        return new MessageResponse("Pet with the pet id " + petId + " has its image with the image id " + imgId + " is deleted successfully.");

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product id", "product", productId));
        Set<Image> prodImages = product.getProdImages();
        if (imgId == ImageConstants.DEFAULT_PRODUCT_IMAGE_ID) throw new AppException(ErrorCode.CANNOT_REMOVE_DEFAULT_IMAGE);
        boolean found = false;
        for (Image image : prodImages) if (image.getImgId() == imgId) {
            found = true; break;
        }
        if (!found) throw new ResourceNotFoundException("pet id", "pet within product", String.format("ProductId: %s, ImageId %s", productId, imgId));

        imageService.deleteImageByImageId(imgId);
        return new MessageResponse(String.format("Product with the product id %s has its pet image with the id %s deleted successfully.", productId, imgId));

    }

    @Override
    public ProductResponse addPetToProduct(Long petId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product id", "product", productId));
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet id", "pet", petId));
        Set<Pet> pets = product.getPets();
        pets.add(pet);


        return mapper.map(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public CustomPage<ProductResponse> getFavoritesProductByUserId(Long userId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByUserId(userId, pageable);


        return CustomPage.<ProductResponse>builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .totalPages(products.getTotalPages())
                .pageContent(products.getContent().stream()
                        .map(product -> mapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public MessageResponse addProductToFavorites(Long userId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product id", "product", productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Set<Product> products = user.getProducts();
        if (products.contains(product))
            throw new AppException(ErrorCode.CANNOT_ADD_PRODUCT_INTO_FAVORITES) ;

        products.add(product);
        user.setProducts(products);

        userRepository.save(user);

        return new MessageResponse(String.format("user with the id %s has had his favorites added the product with the id %s.", userId, productId));
    }

    @Override
    public MessageResponse removeProductFromFavorite(Long userId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product id", "product", productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Set<Product> products = user.getProducts();
        if (!products.contains(product))
            throw new AppException(ErrorCode.CANNOT_REMOVE_PRODUCT_FROM_FAVORITES) ;

        products.remove(product);
        user.setProducts(products);

        userRepository.save(user);

        return new MessageResponse(String.format("user with the id %s has had his favorites removed the product with the id %s.", userId, productId));
    }
}
