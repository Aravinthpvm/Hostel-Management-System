package com.hostelmgmt.controller;

import com.hostelmgmt.dto.request.CreateHostelRequest;
import com.hostelmgmt.dto.response.ApiResponse;
import com.hostelmgmt.model.Hostel;
import com.hostelmgmt.service.HostelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostels")
@RequiredArgsConstructor
public class HostelController {
    
    private final HostelService hostelService;
    
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Hostel> createHostel(@Valid @RequestBody CreateHostelRequest request) {
        Hostel hostel = hostelService.createHostel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(hostel);
    }
    
    @GetMapping
    public ResponseEntity<List<Hostel>> getAllHostels() {
        List<Hostel> hostels = hostelService.getAllHostels();
        return ResponseEntity.ok(hostels);
    }
    
    @GetMapping("/my-hostels")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<Hostel>> getMyHostels() {
        List<Hostel> hostels = hostelService.getMyHostels();
        return ResponseEntity.ok(hostels);
    }
    
    @GetMapping("/{hostelId}")
    public ResponseEntity<Hostel> getHostelById(@PathVariable String hostelId) {
        Hostel hostel = hostelService.getHostelById(hostelId);
        return ResponseEntity.ok(hostel);
    }
    
    @PutMapping("/{hostelId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Hostel> updateHostel(
            @PathVariable String hostelId,
            @Valid @RequestBody CreateHostelRequest request) {
        Hostel hostel = hostelService.updateHostel(hostelId, request);
        return ResponseEntity.ok(hostel);
    }
    
    @DeleteMapping("/{hostelId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse> deleteHostel(@PathVariable String hostelId) {
        hostelService.deleteHostel(hostelId);
        return ResponseEntity.ok(new ApiResponse(true, "Hostel deleted successfully"));
    }
}
