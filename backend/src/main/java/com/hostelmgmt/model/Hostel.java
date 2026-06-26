package com.hostelmgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hostels")
public class Hostel {
    
    @Id
    private String id;
    
    private String name;
    
    private String address;
    
    private String city;
    
    private String state;
    
    private String pincode;
    
    private String description;
    
    private String ownerId; // Reference to User with OWNER role
    
    private String wardenId; // Reference to User with WARDEN role
    
    private List<String> amenities = new ArrayList<>(); // WiFi, Laundry, Gym, etc.
    
    private List<String> images = new ArrayList<>();
    
    private Integer totalFloors;
    
    private Integer totalRooms;
    
    private Double securityDeposit;
    
    private Boolean isActive = true;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
