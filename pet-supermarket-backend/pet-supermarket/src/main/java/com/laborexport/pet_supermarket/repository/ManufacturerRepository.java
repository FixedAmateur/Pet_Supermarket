package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Manufacturer;
import com.laborexport.pet_supermarket.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
