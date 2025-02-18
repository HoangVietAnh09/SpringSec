package com.example.springSec.mapper;


import com.example.springSec.Entity.Permission;
import com.example.springSec.dto.Request.PermissionRequest;
import com.example.springSec.dto.Response.PermissionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
