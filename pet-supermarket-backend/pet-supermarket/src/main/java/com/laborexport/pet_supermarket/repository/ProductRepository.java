package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN p.manufacturer m WHERE m.id = :id")
    Page<Product> findAllByManufacturerId(Long id, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.discounts d WHERE d.id = :id")
    Page<Product> findAllByDiscountId(Long id, Pageable pageable);

    List<Product> findAllByProductName(String productName);

    boolean existsByProductNameAndProductType(String productName, String productType);

    @Query("SELECT p FROM Product p JOIN p.users u WHERE u.id = :userId")
    Page<Product> findAllByUserId(Long userId, Pageable pageable);

}
