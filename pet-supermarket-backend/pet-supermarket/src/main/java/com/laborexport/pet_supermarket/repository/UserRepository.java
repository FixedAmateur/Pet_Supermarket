package com.laborexport.pet_supermarket.repository;

import com.laborexport.pet_supermarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String emailOrPhone);

    boolean existsByUsername(String username);
}
