package com.example.springSec.Service;


import com.example.springSec.Entity.Permission;
import com.example.springSec.Repository.PermissionRepo;
import com.example.springSec.dto.Request.PermissionRequest;
import com.example.springSec.dto.Response.PermissionResponse;
import com.example.springSec.mapper.PermissionMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepo permissionRepo;
    PermissionMapper permissionMapper;


    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepo.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permission = permissionRepo.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String name) {
        permissionRepo.deleteById(name);
    }

}
