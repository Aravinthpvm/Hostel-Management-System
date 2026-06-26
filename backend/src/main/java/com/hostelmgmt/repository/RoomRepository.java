package com.hostelmgmt.repository;

import com.hostelmgmt.model.Room;
import com.hostelmgmt.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    
    List<Room> findByHostelId(String hostelId);
    
    List<Room> findByHostelIdAndStatus(String hostelId, RoomStatus status);
    
    List<Room> findByStatus(RoomStatus status);
    
    List<Room> findByHostelIdAndFloorNumber(String hostelId, Integer floorNumber);
    
    Long countByHostelId(String hostelId);
    
    Long countByHostelIdAndStatus(String hostelId, RoomStatus status);
}
