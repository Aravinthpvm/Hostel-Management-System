package com.hostelmgmt.controller;

import com.hostelmgmt.dto.request.UpdateUserRequest;
import com.hostelmgmt.dto.response.ApiResponse;
import com.hostelmgmt.dto.response.UserResponse;
import com.hostelmgmt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        ApiResponse response = new ApiResponse(true, "User retrieved successfully", user);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String userId) {
        UserResponse user = userService.getUserById(userId);
        ApiResponse response = new ApiResponse(true, "User retrieved successfully", user);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateProfile(request);
        ApiResponse response = new ApiResponse(true, "Profile updated successfully", user);
        return ResponseEntity.ok(response);
    }
}
