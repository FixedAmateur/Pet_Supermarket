package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.exception.ErrorCode;
import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.response.CustomPage;
import com.laborexport.pet_supermarket.model.dto.response.OrderResponse;
import com.laborexport.pet_supermarket.model.entity.Order;
import com.laborexport.pet_supermarket.model.entity.OrderProduct;
import com.laborexport.pet_supermarket.model.entity.User;
import com.laborexport.pet_supermarket.repository.OrderRepository;
import com.laborexport.pet_supermarket.repository.UserRepository;
import com.laborexport.pet_supermarket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public OrderResponse purchaseOrderByUserId(Long userId) {
        // get user cart
        List<Order> carts = orderRepository.findAllByUserIdAndPurchased(userId, false);
        if (carts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CARTS);
        Order cart = carts.get(0);

        // update discounts and subprice of cart products
        cart = updateCartBeforePurchase(cart);

        // make order
        cart.setPurchased(true);
        orderRepository.save(cart);


        //create new empty cart for user
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user id", "user", userId)
        );
        Order newCart = new Order();
        newCart.setUser(user);
        newCart.setOrderUpdated(LocalDateTime.now());
        orderRepository.save(newCart);
        return mapper.map(cart, OrderResponse.class);
    }

    @Override
    public CustomPage<OrderResponse> getAllOrderByUserId(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserId(userId, pageable);
        return CustomPage.<OrderResponse>builder()
                .pageNo(orders.getNumber()+1)
                .pageSize(orders.getSize())
                .pageContent(orders.getContent().stream().map(
                        order -> mapper.map(order, OrderResponse.class)
                    ).collect(Collectors.toList()))
                .build();
    }

    @Override
    public CustomPage<OrderResponse> getAllPurchasedOrderByUsedId(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserIdAndPurchased(userId, true, pageable);
        return CustomPage.<OrderResponse>builder()
                .pageNo(orders.getNumber()+1)
                .pageSize(orders.getSize())
                .pageContent(orders.getContent().stream().map(
                        order -> mapper.map(order, OrderResponse.class)
                ).collect(Collectors.toList()))
                .build();
    }

    @Override
    public OrderResponse getCartByUserId(Long userId) {
        List<Order> carts = orderRepository.findAllByUserIdAndPurchased(userId, false);
        if (carts.size() != 1) throw new AppException(ErrorCode.INVALID_NUMBER_OF_CARTS);
        Order cart = carts.get(0);

        return mapper.map(cart, OrderResponse.class);
    }

    private Order updateCartBeforePurchase(Order cart) {
        Set<OrderProduct> cartProducts = cart.getOrderProducts();

        for (OrderProduct cartProduct : cartProducts) {
            cartProduct = OrderProduct.updateSubPriceAndDiscount(cartProduct);
        }
        cart.setOrderProducts(cartProducts);
        return cart;
    }


}
