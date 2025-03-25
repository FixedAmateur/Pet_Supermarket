package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {


    @Query("from OrderProduct op where op.order.user.id=:userId and op.product.id=:productId and op.order.purchased=:purchased")
    List<OrderProduct> findAllByUserIdAndProductIdAndPurchased(Long userId, Long productId, boolean purchased);
}
