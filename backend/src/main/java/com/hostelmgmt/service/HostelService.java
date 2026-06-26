package com.hostelmgmt.service;

import com.hostelmgmt.dto.request.CreateHostelRequest;
import com.hostelmgmt.exception.BadRequestException;
import com.hostelmgmt.exception.ResourceNotFoundException;
import com.hostelmgmt.exception.UnauthorizedException;
import com.hostelmgmt.model.Hostel;
import com.hostelmgmt.model.User;
import com.hostelmgmt.model.enums.UserRole;
import com.hostelmgmt.repository.HostelRepository;
import com.hostelmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HostelService {
    
    private final HostelRepository hostelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    
    public Hostel createHostel(CreateHostelRequest request) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        // Only OWNER can create hostels
        if (currentUser.getRole() != UserRole.OWNER) {
            throw new UnauthorizedException("Only owners can create hostels");
        }
        
        // Validate warden if provided
        if (request.getWardenId() != null && !request.getWardenId().isEmpty()) {
            User warden = userRepository.findById(request.getWardenId())
                .orElseThrow(() -> new BadRequestException("Warden not found"));
            
            if (warden.getRole() != UserRole.WARDEN) {
                throw new BadRequestException("Selected user is not a warden");
            }
        }
        
        Hostel hostel = new Hostel();
        hostel.setName(request.getName());
        hostel.setAddress(request.getAddress());
        hostel.setCity(request.getCity());
        hostel.setState(request.getState());
        hostel.setPincode(request.getPincode());
        hostel.setDescription(request.getDescription());
        hostel.setOwnerId(currentUser.getId());
        hostel.setWardenId(request.getWardenId());
        hostel.setAmenities(request.getAmenities());
        hostel.setImages(request.getImages());
        hostel.setTotalFloors(request.getTotalFloors());
        hostel.setTotalRooms(0); // Will be updated when rooms are added
        hostel.setSecurityDeposit(request.getSecurityDeposit());
        hostel.setIsActive(true);
        
        Hostel savedHostel = hostelRepository.save(hostel);
        log.info("Hostel created: {} by owner: {}", savedHostel.getName(), currentUser.getEmail());
        
        return savedHostel;
    }
    
    public List<Hostel> getAllHostels() {
        return hostelRepository.findByIsActive(true);
    }
    
    public List<Hostel> getMyHostels() {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        if (currentUser.getRole() != UserRole.OWNER) {
            throw new UnauthorizedException("Only owners can view their hostels");
        }
        
        return hostelRepository.findByOwnerIdAndIsActive(currentUser.getId(), true);
    }
    
    public Hostel getHostelById(String hostelId) {
        return hostelRepository.findById(hostelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
    }
    
    public Hostel updateHostel(String hostelId, CreateHostelRequest request) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Hostel hostel = hostelRepository.findById(hostelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        
        // Only the owner of the hostel can update it
        if (!hostel.getOwnerId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this hostel");
        }
        
        // Update fields
        hostel.setName(request.getName());
        hostel.setAddress(request.getAddress());
        hostel.setCity(request.getCity());
        hostel.setState(request.getState());
        hostel.setPincode(request.getPincode());
        hostel.setDescription(request.getDescription());
        hostel.setWardenId(request.getWardenId());
        hostel.setAmenities(request.getAmenities());
        hostel.setImages(request.getImages());
        hostel.setTotalFloors(request.getTotalFloors());
        hostel.setSecurityDeposit(request.getSecurityDeposit());
        
        Hostel updatedHostel = hostelRepository.save(hostel);
        log.info("Hostel updated: {}", updatedHostel.getName());
        
        return updatedHostel;
    }
    
    public void deleteHostel(String hostelId) {
        String currentUserEmail = userService.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Hostel hostel = hostelRepository.findById(hostelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        
        // Only the owner of the hostel can delete it
        if (!hostel.getOwnerId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this hostel");
        }
        
        // Soft delete
        hostel.setIsActive(false);
        hostelRepository.save(hostel);
        
        log.info("Hostel deleted: {}", hostel.getName());
    }
}
