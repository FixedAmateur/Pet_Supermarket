package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order o where o.user.id = :userId and o.purchased = :purchased")
    List<Order> findAllByUserIdAndPurchased(Long userId, boolean purchased);

    @Query("from Order o where o.user.id = :userId")
    Page<Order> findAllByUserId(Long usedId, Pageable pageable);

    @Query("from Order o where o.user.id = :userId and o.purchased = :purchased")
    Page<Order> findAllByUserIdAndPurchased(Long userId, boolean purchased, Pageable pageable);

//    @Query("from Order o where o.user.id = :userId and o.product.id=:productId and o.purchased = :purchased")
//    List<Order> findAllByUserIdAndProductIdAndPurchased(Long userId, Long productId, boolean purchased);
}
