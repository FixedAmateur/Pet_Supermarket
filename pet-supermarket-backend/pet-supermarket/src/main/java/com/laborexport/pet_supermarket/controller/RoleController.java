package com.laborexport.pet_supermarket.controller;

import com.laborexport.pet_supermarket.model.dto.request.RoleRequest;
import com.laborexport.pet_supermarket.model.dto.response.ApiResponse;
import com.laborexport.pet_supermarket.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {
    private final RoleService roleService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<ApiResponse> createRole (@Valid @RequestBody RoleRequest request){
        ApiResponse apiResponse = ApiResponse.succeed(roleService.createRole(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/name/{roleName}")
    public ResponseEntity<ApiResponse> getRoleByName(@PathVariable("roleName") String roleName){
        ApiResponse apiResponse = ApiResponse.succeed(roleService.getRoleByRoleName(roleName));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable("roleId") Long roleId){
        ApiResponse apiResponse = ApiResponse.succeed(roleService.getRoleById(roleId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllRole(){
        ApiResponse apiResponse = ApiResponse.succeed(roleService.getAllRole());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse> deleteRoleByName(@PathVariable("roleId") Long roleId){
        ApiResponse apiResponse = ApiResponse.succeed(roleService.deleteRole(roleId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
