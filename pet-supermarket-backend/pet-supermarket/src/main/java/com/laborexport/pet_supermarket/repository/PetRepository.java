package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
