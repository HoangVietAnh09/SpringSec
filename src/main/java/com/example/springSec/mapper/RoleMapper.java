package com.example.springSec.mapper;


import com.example.springSec.Entity.Role;
import com.example.springSec.dto.Request.RoleRequest;
import com.example.springSec.dto.Response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
