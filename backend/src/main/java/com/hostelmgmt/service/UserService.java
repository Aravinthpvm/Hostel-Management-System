package com.hostelmgmt.service;

import com.hostelmgmt.dto.request.UpdateUserRequest;
import com.hostelmgmt.dto.response.UserResponse;
import com.hostelmgmt.exception.ResourceNotFoundException;
import com.hostelmgmt.exception.UnauthorizedException;
import com.hostelmgmt.model.User;
import com.hostelmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    public UserResponse getCurrentUser() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        return mapToUserResponse(user);
    }
    
    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return mapToUserResponse(user);
    }
    
    public UserResponse updateProfile(UpdateUserRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        // Update fields if provided
        if (request.getFullName() != null && !request.getFullName().isEmpty()) {
            user.setFullName(request.getFullName());
        }
        
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            user.setProfileImage(request.getProfileImage());
        }
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User profile updated: {}", updatedUser.getEmail());
        
        return mapToUserResponse(updatedUser);
    }
    
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setProfileImage(user.getProfileImage());
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
