package com.hostelmgmt.repository;

import com.hostelmgmt.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, String> {
    
    List<Hostel> findByOwnerId(String ownerId);
    
    List<Hostel> findByCity(String city);
    
    List<Hostel> findByIsActive(Boolean isActive);
    
    List<Hostel> findByOwnerIdAndIsActive(String ownerId, Boolean isActive);
}
