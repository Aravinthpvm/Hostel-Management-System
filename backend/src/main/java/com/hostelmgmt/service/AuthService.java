package com.hostelmgmt.service;

import com.hostelmgmt.dto.request.LoginRequest;
import com.hostelmgmt.dto.request.RegisterRequest;
import com.hostelmgmt.dto.response.AuthResponse;
import com.hostelmgmt.exception.BadRequestException;
import com.hostelmgmt.exception.UnauthorizedException;
import com.hostelmgmt.model.User;
import com.hostelmgmt.repository.UserRepository;
import com.hostelmgmt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        
        log.info("User registered successfully: {}", savedUser.getEmail());
        
        // Generate JWT token
        String token = jwtUtil.generateToken(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getRole().toString()
        );
        
        return new AuthResponse(
            token,
            savedUser.getId(),
            savedUser.getFullName(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        
        // Check if account is active
        if (!user.getIsActive()) {
            throw new UnauthorizedException("Account is deactivated");
        }
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        
        log.info("User logged in successfully: {}", user.getEmail());
        
        // Generate JWT token
        String token = jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole().toString()
        );
        
        return new AuthResponse(
            token,
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole()
        );
    }
}
