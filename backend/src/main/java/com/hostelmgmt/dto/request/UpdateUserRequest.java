package com.hostelmgmt.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String fullName;
    
    private String phoneNumber;
    
    private String profileImage;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
