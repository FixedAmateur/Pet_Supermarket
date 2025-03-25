package com.laborexport.pet_supermarket;

import com.laborexport.pet_supermarket.exception.AppException;
import com.laborexport.pet_supermarket.model.entity.Role;
import com.laborexport.pet_supermarket.repository.RoleRepository;
import com.laborexport.pet_supermarket.service.RoleService;
import com.laborexport.pet_supermarket.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PetSupermarketApplication {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {


		SpringApplication.run(PetSupermarketApplication.class, args);
	}
	public void run(String... args) throws Exception {
		try {
			Role customerRole = new Role();
			Role adminRole = new Role();
			if (!roleRepository.existsByRoleName("ADMIN")) {

				adminRole.setRoleName("ADMIN");
				roleRepository.save(adminRole);
			}
			if (!roleRepository.existsByRoleName("CUSTOMER")) {

				customerRole.setRoleName("CUSTOMER");
				roleRepository.save(customerRole);
			}



			List<Role> roles =roleRepository.findAll();

			roles.forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
