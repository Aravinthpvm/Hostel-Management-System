package com.hostelmgmt.service;

import com.hostelmgmt.dto.request.CreateRoomRequest;
import com.hostelmgmt.exception.BadRequestException;
import com.hostelmgmt.exception.ResourceNotFoundException;
import com.hostelmgmt.exception.UnauthorizedException;
import com.hostelmgmt.model.Hostel;
import com.hostelmgmt.model.Room;
import com.hostelmgmt.model.User;
import com.hostelmgmt.model.enums.RoomStatus;
import com.hostelmgmt.model.enums.UserRole;
import com.hostelmgmt.repository.HostelRepository;
import com.hostelmgmt.repository.RoomRepository;
import com.hostelmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    
    private final RoomRepository roomRepository;
    private final HostelRepository hostelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    
    public Room createRoom(CreateRoomRequest request) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        // Only OWNER can create rooms
        if (currentUser.getRole() != UserRole.OWNER) {
            throw new UnauthorizedException("Only owners can create rooms");
        }
        
        Hostel hostel = hostelRepository.findById(request.getHostelId())
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        
        // Verify the owner owns this hostel
        if (!hostel.getOwnerId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to add rooms to this hostel");
        }
        
        // Validate floor number
        if (request.getFloorNumber() > hostel.getTotalFloors()) {
            throw new BadRequestException("Floor number exceeds hostel's total floors");
        }
        
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setHostelId(request.getHostelId());
        room.setFloorNumber(request.getFloorNumber());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setCurrentOccupancy(0);
        room.setRentPerMonth(request.getRentPerMonth());
        room.setStatus(RoomStatus.AVAILABLE);
        room.setAmenities(request.getAmenities());
        room.setImages(request.getImages());
        room.setArea(request.getArea());
        room.setDescription(request.getDescription());
        
        Room savedRoom = roomRepository.save(room);
        
        // Update hostel's total rooms count
        Long totalRooms = roomRepository.countByHostelId(hostel.getId());
        hostel.setTotalRooms(totalRooms.intValue());
        hostelRepository.save(hostel);
        
        log.info("Room created: {} in hostel: {}", savedRoom.getRoomNumber(), hostel.getName());
        
        return savedRoom;
    }
    
    public List<Room> getRoomsByHostelId(String hostelId) {
        return roomRepository.findByHostelId(hostelId);
    }
    
    public List<Room> getAvailableRoomsByHostelId(String hostelId) {
        return roomRepository.findByHostelIdAndStatus(hostelId, RoomStatus.AVAILABLE);
    }
    
    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }
    
    public Room updateRoom(String roomId, CreateRoomRequest request) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        
        Hostel hostel = hostelRepository.findById(room.getHostelId())
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        
        // Only the owner of the hostel can update rooms
        if (!hostel.getOwnerId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this room");
        }
        
        // Update fields
        room.setRoomNumber(request.getRoomNumber());
        room.setFloorNumber(request.getFloorNumber());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setRentPerMonth(request.getRentPerMonth());
        room.setAmenities(request.getAmenities());
        room.setImages(request.getImages());
        room.setArea(request.getArea());
        room.setDescription(request.getDescription());
        
        Room updatedRoom = roomRepository.save(room);
        log.info("Room updated: {}", updatedRoom.getRoomNumber());
        
        return updatedRoom;
    }
    
    public void deleteRoom(String roomId) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        
        Hostel hostel = hostelRepository.findById(room.getHostelId())
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        
        // Only the owner can delete rooms
        if (!hostel.getOwnerId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this room");
        }
        
        // Cannot delete if room is occupied
        if (room.getCurrentOccupancy() > 0) {
            throw new BadRequestException("Cannot delete an occupied room");
        }
        
        roomRepository.delete(room);
        
        // Update hostel's total rooms count
        Long totalRooms = roomRepository.countByHostelId(hostel.getId());
        hostel.setTotalRooms(totalRooms.intValue());
        hostelRepository.save(hostel);
        
        log.info("Room deleted: {}", room.getRoomNumber());
    }
    
    public Room updateRoomStatus(String roomId, RoomStatus status) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        
        room.setStatus(status);
        return roomRepository.save(room);
    }
}
