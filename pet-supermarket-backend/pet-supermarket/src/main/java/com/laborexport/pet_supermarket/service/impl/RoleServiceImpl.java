package com.laborexport.pet_supermarket.service.impl;

import com.laborexport.pet_supermarket.exception.ResourceNotFoundException;
import com.laborexport.pet_supermarket.model.dto.request.RoleRequest;
import com.laborexport.pet_supermarket.model.dto.response.MessageResponse;
import com.laborexport.pet_supermarket.model.dto.response.RoleResponse;
import com.laborexport.pet_supermarket.model.entity.Role;
import com.laborexport.pet_supermarket.repository.RoleRepository;
import com.laborexport.pet_supermarket.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final ModelMapper modelMapper;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest request){
        Role role = modelMapper.map(request, Role.class);
        return modelMapper.map(roleRepository.save(role),RoleResponse.class);
    }

    @Override
    public RoleResponse getRoleByRoleName(String roleName){
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleName));
        return modelMapper.map(role,RoleResponse.class);
    }

    @Override
    public RoleResponse getRoleById(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleId));
        return modelMapper.map(role,RoleResponse.class);
    }

    @Override
    public List<RoleResponse> getAllRole(){
        return roleRepository.findAll().stream()
                .map(result->modelMapper.map(result,RoleResponse.class)).toList();
    }

    @Override
    public MessageResponse deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleId));
        roleRepository.delete(role);
        return new MessageResponse("Role with role id " + roleId + " was deleted successfully");
    }

    @Override
    public boolean existsByRoleName(String name) {
        return roleRepository.existsByRoleName(name);
    }

}
