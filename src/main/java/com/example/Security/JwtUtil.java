package com.example.Security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
 @Value("${jwt.secret}")
 private String secret;

 public String generateToken(String username) {
     return Jwts.builder()
         .setSubject(username)
         .setIssuedAt(new Date())
         .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
         .signWith(SignatureAlgorithm.HS512, secret)
         .compact();
 }

 public String getUsernameFromToken(String token) {
     return Jwts.parser()
         .setSigningKey(secret)
         .parseClaimsJws(token)
         .getBody()
         .getSubject();
 }

 public boolean validateToken(String token) {
     try {
         Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
         return true;
     } catch (Exception e) {
         return false;
     }
 }
}