package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.OrderProductRequest;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderProductResponse;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;
import com.laborexport.pet_supermarket.model.entity.Order;
import com.laborexport.pet_supermarket.model.entity.OrderProduct;
import com.laborexport.pet_supermarket.model.entity.Product;
import com.laborexport.pet_supermarket.repository.OrderProductRepository;
import com.laborexport.pet_supermarket.repository.OrderRepository;
import com.laborexport.pet_supermarket.repository.ProductRepository;
import com.laborexport.pet_supermarket.service.OrderProductService;
import com.laborexport.pet_supermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderProductRepository orderProductRepository;
    @Autowired
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public OrderProductResponse addProductToCartByUserId(Long userId, Long productId, OrderProductRequest request) {
        //get user cart
        List<Order> carts = orderRepository.findAllByUserIdAndPurchased(userId, false);
        if (carts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CARTS);
        Order cart = carts.get(0);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("used id", "user", productId));

        OrderProduct addedCartProduct = mapper.map(request, OrderProduct.class);
        addedCartProduct.setProduct(product);
        addedCartProduct.setOrder(cart);
        addedCartProduct = OrderProduct.updateSubPriceAndDiscount(addedCartProduct);

        cart.setTotalPrice(cart.getTotalPrice() + addedCartProduct.getSubPrice());
        cart.setQuantity(cart.getQuantity() + addedCartProduct.getSubQuantity());
        orderRepository.save(cart);

        return mapper.map(orderProductRepository.save(addedCartProduct), OrderProductResponse.class);
    }

    @Override
    public MessageResponse removeProductFromCartByUserIdAndProductId(Long userId, Long productId) {
        //get user cart product
        List<OrderProduct> cartProducts = orderProductRepository.findAllByUserIdAndProductIdAndPurchased(userId, productId, false);
        if (cartProducts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CART_PRODUCTS);
        OrderProduct cartProduct = cartProducts.get(0);

        orderProductRepository.delete(cartProduct);
        return new MessageResponse(String.format("User with id %s has the product with the id %s removed from his cart successfully.", userId, productId));
    }

    @Override
    public OrderProductResponse updateCartProductByUserIdAndProductId(Long userId, Long productId, OrderProductRequest request) {
        //get user cart
        List<OrderProduct> cartProducts = orderProductRepository.findAllByUserIdAndProductIdAndPurchased(userId, productId, false);
        if (cartProducts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CART_PRODUCTS);
        OrderProduct cartProduct = cartProducts.get(0);

        //get product
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("used id", "user", productId));

        // map product
        cartProduct = mapper.map(request, OrderProduct.class);
        cartProduct.setProduct(product);

        return mapper.map(orderProductRepository.save(cartProduct), OrderProductResponse.class);
    }

    @Override
    public OrderResponse updateCartProductDiscountByUserId(Long userId) {
        List<Order> carts = orderRepository.findAllByUserIdAndPurchased(userId, false);
        if (carts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CARTS);
        Order cart = carts.get(0);

        for (OrderProduct cartProduct : cart.getOrderProducts()) {
            cartProduct = OrderProduct.updateSubPriceAndDiscount(cartProduct);
            orderProductRepository.save(cartProduct);
        }
        return mapper.map(orderRepository.save(cart), OrderResponse.class);
    }



}
