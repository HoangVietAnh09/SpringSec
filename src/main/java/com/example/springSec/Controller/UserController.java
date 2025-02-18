package com.example.springSec.Controller;


import com.example.springSec.Entity.User;
import com.example.springSec.Service.UserService;
import com.example.springSec.dto.Request.AuthenRequest;
import com.example.springSec.dto.Request.UserCreateRequest;
import com.example.springSec.dto.Request.UserUpdateRequest;
import com.example.springSec.dto.Response.ApiResponse;
import com.example.springSec.dto.Response.LoginResponse;
import com.example.springSec.dto.Response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;


    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody AuthenRequest authenRequest){
        return ApiResponse.<LoginResponse>builder()
                .result(userService.login(authenRequest))
                .build();
    }

    @GetMapping("/test")
    public String test(){
        log.info("test");
        return "test";
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/myinfo")
    public ApiResponse<UserResponse> myInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(g -> log.info("GrantedAuthority: {}", g.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request){

        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "Deleted";
    }


}
