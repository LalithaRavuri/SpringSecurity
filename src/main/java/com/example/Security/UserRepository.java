package com.example.Security;


import com.example.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 // Find user by username
 Optional<User> findByUsername(String username);
 
 // Check if username exists
 boolean existsByUsername(String username);
 
 // Find active users by username
 Optional<User> findByUsernameAndActiveTrue(String username);
 
 // Custom query example
 @Query("SELECT u FROM User u WHERE u.username = :username AND u.active = true")
 Optional<User> findActiveUser(@Param("username") String username);
}