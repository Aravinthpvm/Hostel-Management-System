package com.hostelmgmt.model;

import com.hostelmgmt.model.enums.RoomStatus;
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
@Document(collection = "rooms")
public class Room {
    
    @Id
    private String id;
    
    private String roomNumber;
    
    private String hostelId; // Reference to Hostel
    
    private Integer floorNumber;
    
    private String roomType; // Single, Double, Triple, Dormitory
    
    private Integer capacity; // Maximum occupants
    
    private Integer currentOccupancy = 0;
    
    private Double rentPerMonth;
    
    private RoomStatus status = RoomStatus.AVAILABLE;
    
    private List<String> amenities = new ArrayList<>(); // AC, Attached Bathroom, Balcony, etc.
    
    private List<String> images = new ArrayList<>();
    
    private Double area; // in square feet
    
    private String description;
    
    private List<String> currentOccupants = new ArrayList<>(); // List of Student User IDs
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
