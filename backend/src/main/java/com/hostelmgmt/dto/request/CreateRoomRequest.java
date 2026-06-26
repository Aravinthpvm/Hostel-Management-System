package com.hostelmgmt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRoomRequest {
    
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    
    @NotBlank(message = "Hostel ID is required")
    private String hostelId;
    
    @NotNull(message = "Floor number is required")
    private Integer floorNumber;
    
    @NotBlank(message = "Room type is required")
    private String roomType; // Single, Double, Triple, Dormitory
    
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;
    
    @NotNull(message = "Rent is required")
    @Positive(message = "Rent must be positive")
    private Double rentPerMonth;
    
    private List<String> amenities = new ArrayList<>();
    
    private List<String> images = new ArrayList<>();
    
    private Double area;
    
    private String description;
}
