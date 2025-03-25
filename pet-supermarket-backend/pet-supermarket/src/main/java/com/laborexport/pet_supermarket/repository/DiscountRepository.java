package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Page<Discount> findAll(Pageable pageable);
}
