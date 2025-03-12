package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
