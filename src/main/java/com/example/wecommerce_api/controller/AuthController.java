package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.payload.BaseResponse;
import com.example.wecommerce_api.payload.auth.PhoneLoginRequest;
import com.example.wecommerce_api.payload.auth.RegisterRequest;
import jakarta.validation.Valid;
import com.example.wecommerce_api.service.Auth.AuthenticationService;
import com.example.wecommerce_api.service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService service;

    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterRequest request) throws Exception{
        return service.register(request);
    }
    @PostMapping("/loginPhoneNumber")
    public BaseResponse loginByPhoneNumber(@Valid @RequestBody PhoneLoginRequest request) throws Exception {
        return service.LoginByPhoneNumber(request.getPhoneNumber());
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }



}
