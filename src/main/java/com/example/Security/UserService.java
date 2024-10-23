package com.example.Security;

import com.example.Security.User;
import com.example.Security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

 public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
     this.userRepository = userRepository;
     this.passwordEncoder = passwordEncoder;
 }

 public User createUser(String username, String password) {
     // Check if username exists
     if (userRepository.existsByUsername(username)) {
         throw new RuntimeException("Username already exists");
     }

     // Create new user
     User user = new User();
     user.setUsername(username);
     user.setPassword(passwordEncoder.encode(password));
     
     return userRepository.save(user);
 }

 public User findByUsername(String username) {
     return userRepository.findByUsername(username)
         .orElseThrow(() -> new RuntimeException("User not found"));
 }

 public boolean validateUser(String username, String password) {
     User user = findByUsername(username);
     return passwordEncoder.matches(password, user.getPassword());
 }
}