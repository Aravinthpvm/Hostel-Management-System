package com.hostelmgmt.controller;

import com.hostelmgmt.dto.request.LoginRequest;
import com.hostelmgmt.dto.request.RegisterRequest;
import com.hostelmgmt.dto.response.ApiResponse;
import com.hostelmgmt.dto.response.AuthResponse;
import com.hostelmgmt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse response = new ApiResponse(true, "Registration successful", authResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse response = new ApiResponse(true, "Login successful", authResponse);
        return ResponseEntity.ok(response);
    }
}
