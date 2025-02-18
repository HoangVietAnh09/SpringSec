package com.example.springSec.Service;


import com.example.springSec.Entity.User;
import com.example.springSec.Enum.Role;
import com.example.springSec.Exception.AppException;
import com.example.springSec.Exception.ErrorCode;
import com.example.springSec.Repository.RoleRepo;
import com.example.springSec.dto.Request.AuthenRequest;
import com.example.springSec.dto.Response.AuthenResponse;
import com.example.springSec.dto.Response.LoginResponse;
import com.example.springSec.dto.Response.UserResponse;
import com.example.springSec.mapper.UserMapper;
import com.example.springSec.Repository.UserRepo;
import com.example.springSec.dto.Request.UserCreateRequest;
import com.example.springSec.dto.Request.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepo userRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepo roleRepository;

    public LoginResponse login(AuthenRequest request) {
        User use = userRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(use.getUsername());
        loginResponse.setPassword(use.getPassword());
        return loginResponse;
    }

    public UserResponse createUser(UserCreateRequest request) {
        if(userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);

        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);


        return userMapper.toUserResponse(userRepo.save(user));
    }

    public UserResponse getMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public List<UserResponse> getAllUser() {
//        List<UserResponse> list = new ArrayList<>();
//        for(User user : userRepo.findAll()) {
//            UserResponse userResponse = userMapper.toUserResponse(user);
//            list.add(userResponse);
//        }
        return userRepo.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }


    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepo.save(user));
    }

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

}
