package com.hostelmgmt.controller;

import com.hostelmgmt.dto.request.CreateRoomRequest;
import com.hostelmgmt.dto.response.ApiResponse;
import com.hostelmgmt.model.Room;
import com.hostelmgmt.model.enums.RoomStatus;
import com.hostelmgmt.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    
    private final RoomService roomService;
    
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        Room room = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }
    
    @GetMapping("/hostel/{hostelId}")
    public ResponseEntity<List<Room>> getRoomsByHostelId(@PathVariable String hostelId) {
        List<Room> rooms = roomService.getRoomsByHostelId(hostelId);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/hostel/{hostelId}/available")
    public ResponseEntity<List<Room>> getAvailableRoomsByHostelId(@PathVariable String hostelId) {
        List<Room> rooms = roomService.getAvailableRoomsByHostelId(hostelId);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable String roomId) {
        Room room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }
    
    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Room> updateRoom(
            @PathVariable String roomId,
            @Valid @RequestBody CreateRoomRequest request) {
        Room room = roomService.updateRoom(roomId, request);
        return ResponseEntity.ok(room);
    }
    
    @PatchMapping("/{roomId}/status")
    @PreAuthorize("hasAnyRole('OWNER', 'WARDEN')")
    public ResponseEntity<Room> updateRoomStatus(
            @PathVariable String roomId,
            @RequestParam RoomStatus status) {
        Room room = roomService.updateRoomStatus(roomId, status);
        return ResponseEntity.ok(room);
    }
    
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok(new ApiResponse(true, "Room deleted successfully"));
    }
}
