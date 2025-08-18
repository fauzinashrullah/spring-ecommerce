package com.ecommerce.fauzi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.fauzi.dto.request.LoginRequest;
import com.ecommerce.fauzi.dto.request.RegisterRequest;
import com.ecommerce.fauzi.dto.response.ApiResponse;
import com.ecommerce.fauzi.dto.response.LoginResponse;
import com.ecommerce.fauzi.service.AuthService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Register success", Map.of()));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse response = service.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login success", response));
    }
    
}
