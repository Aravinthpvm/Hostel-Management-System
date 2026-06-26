package com.hostelmgmt.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "hostels")
@EntityListeners(AuditingEntityListener.class)
public class Hostel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String name;
    
    private String address;
    
    private String city;
    
    private String state;
    
    private String pincode;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String ownerId; // Reference to User with OWNER role
    
    private String wardenId; // Reference to User with WARDEN role
    
    @ElementCollection
    @CollectionTable(name = "hostel_amenities", joinColumns = @JoinColumn(name = "hostel_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>(); // WiFi, Laundry, Gym, etc.
    
    @ElementCollection
    @CollectionTable(name = "hostel_images", joinColumns = @JoinColumn(name = "hostel_id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();
    
    private Integer totalFloors;
    
    private Integer totalRooms;
    
    private Double securityDeposit;
    
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
