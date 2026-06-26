package com.hostelmgmt.repository;

import com.hostelmgmt.model.User;
import com.hostelmgmt.model.enums.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    Boolean existsByEmail(String email);
    
    Long countByRole(UserRole role);
}
