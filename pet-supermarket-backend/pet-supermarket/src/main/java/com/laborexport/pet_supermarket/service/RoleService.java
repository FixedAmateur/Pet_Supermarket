package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.dto.request.RoleRequest;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole (RoleRequest request);

    RoleResponse getRoleByRoleName(String roleName);

    RoleResponse getRoleById(Long roleId);

    List<RoleResponse> getAllRole();

    MessageResponse deleteRole(Long roleId);

    boolean existsByRoleName(String name);

}
