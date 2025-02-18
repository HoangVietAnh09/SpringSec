package com.example.springSec.Service;


import com.example.springSec.Entity.Role;
import com.example.springSec.Repository.PermissionRepo;
import com.example.springSec.Repository.RoleRepo;
import com.example.springSec.dto.Request.RoleRequest;
import com.example.springSec.dto.Response.RoleResponse;
import com.example.springSec.mapper.RoleMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepo roleRepo;
    PermissionRepo permissionRepo;
    RoleMapper roleMapper;


    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepo.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepo.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        var role =  roleRepo.findAll();
        return role.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String name) {
        roleRepo.deleteById(name);
    }
}
