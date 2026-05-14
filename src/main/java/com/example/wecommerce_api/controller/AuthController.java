package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.payload.BaseResponse;
import com.example.wecommerce_api.payload.auth.AuthenticationRequest;
import com.example.wecommerce_api.payload.auth.RegisterRequest;
import com.example.wecommerce_api.service.Auth.AuthenticationService;
import com.example.wecommerce_api.service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private final UserService userService;
    private final AuthenticationService service;
    public AuthController(UserService userService, AuthenticationService service) {
        this.userService = userService;
        this.service = service;
    }
    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterRequest request) throws Exception{
        return service.register(request);
    }
    @PostMapping("/loginPhoneNumber/{phoneNumber}")
    public BaseResponse loginByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber
    ) throws Exception {
        return service.LoginByPhoneNumber(phoneNumber);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }



}
