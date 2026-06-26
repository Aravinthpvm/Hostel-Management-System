package com.hostelmgmt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateHostelRequest {
    
    @NotBlank(message = "Hostel name is required")
    private String name;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Pincode is required")
    private String pincode;
    
    private String description;
    
    private String wardenId;
    
    private List<String> amenities = new ArrayList<>();
    
    private List<String> images = new ArrayList<>();
    
    @NotNull(message = "Total floors is required")
    @Positive(message = "Total floors must be positive")
    private Integer totalFloors;
    
    @Positive(message = "Security deposit must be positive")
    private Double securityDeposit;
}
