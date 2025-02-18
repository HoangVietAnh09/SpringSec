package com.example.springSec.Controller;


import com.example.springSec.Service.AuthenService;
import com.example.springSec.dto.Request.AuthenRequest;
import com.example.springSec.dto.Request.IntrospectRequest;
import com.example.springSec.dto.Response.ApiResponse;
import com.example.springSec.dto.Response.AuthenResponse;
import com.example.springSec.dto.Response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenController {
    AuthenService authenService;

    @PostMapping("/token")
    public ApiResponse<AuthenResponse> login(@RequestBody AuthenRequest authenRequest) {
        AuthenResponse res = authenService.authen(authenRequest);
        return ApiResponse.<AuthenResponse>builder()
                .result(res)
                .build();
    }

    @PostMapping("/instrospect")
    public ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest authenRequest) throws ParseException, JOSEException {
        IntrospectResponse res = authenService.introspect(authenRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(res)
                .build();
    }

}
