package com.example.Security;

import com.example.Security.LoginRequest;
import com.example.Security.LoginResponse;
import com.example.Security.UserService;
import com.example.Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
 private final UserService userService;
 private final JwtUtil jwtUtil;

 public AuthController(UserService userService, JwtUtil jwtUtil) {
     this.userService = userService;
     this.jwtUtil = jwtUtil;
 }

 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody LoginRequest request) {
     try {
         // Validate user
         if (userService.validateUser(request.getUsername(), request.getPassword())) {
             // Get user details
             User user = userService.findByUsername(request.getUsername());
             
             // Generate token
             String token = jwtUtil.generateToken(user.getUsername());
             
             // Create response
             LoginResponse response = new LoginResponse(
                 token,
                 user.getUsername(),
                 user.getRole()
             );
             
             return ResponseEntity.ok(response);
         }
         
         return ResponseEntity.badRequest().body("Invalid credentials");
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
     }
 }

 @PostMapping("/register")
 public ResponseEntity<?> register(@RequestBody LoginRequest request) {
     try {
         // Create user
         User user = userService.createUser(
             request.getUsername(), 
             request.getPassword()
         );
         
         return ResponseEntity.ok("User registered successfully");
     } catch (Exception e) {
         return ResponseEntity.badRequest()
             .body("Registration failed: " + e.getMessage());
     }
 }
}
