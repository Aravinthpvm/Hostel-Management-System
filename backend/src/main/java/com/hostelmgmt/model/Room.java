package com.hostelmgmt.model;

import com.hostelmgmt.model.enums.RoomStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
@EntityListeners(AuditingEntityListener.class)
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String roomNumber;
    
    private String hostelId; // Reference to Hostel
    
    private Integer floorNumber;
    
    private String roomType; // Single, Double, Triple, Dormitory
    
    private Integer capacity; // Maximum occupants
    
    private Integer currentOccupancy = 0;
    
    private Double rentPerMonth;
    
    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;
    
    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>(); // AC, Attached Bathroom, Balcony, etc.
    
    @ElementCollection
    @CollectionTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();
    
    private Double area; // in square feet
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "room_current_occupants", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "user_id")
    private List<String> currentOccupants = new ArrayList<>(); // List of Student User IDs
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
