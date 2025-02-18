package com.example.springSec.mapper;


import com.example.springSec.Entity.User;
import com.example.springSec.dto.Request.UserCreateRequest;
import com.example.springSec.dto.Request.UserUpdateRequest;
import com.example.springSec.dto.Response.UserResponse;
import org.hibernate.annotations.SQLSelect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);



    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);

}
