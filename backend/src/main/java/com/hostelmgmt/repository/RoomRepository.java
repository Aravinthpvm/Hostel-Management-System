package com.hostelmgmt.repository;

import com.hostelmgmt.model.Room;
import com.hostelmgmt.model.enums.RoomStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    
    List<Room> findByHostelId(String hostelId);
    
    List<Room> findByHostelIdAndStatus(String hostelId, RoomStatus status);
    
    List<Room> findByStatus(RoomStatus status);
    
    List<Room> findByHostelIdAndFloorNumber(String hostelId, Integer floorNumber);
    
    Long countByHostelId(String hostelId);
    
    Long countByHostelIdAndStatus(String hostelId, RoomStatus status);
}
