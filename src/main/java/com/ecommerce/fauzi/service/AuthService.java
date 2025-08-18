package com.ecommerce.fauzi.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.fauzi.dto.request.LoginRequest;
import com.ecommerce.fauzi.dto.request.RegisterRequest;
import com.ecommerce.fauzi.dto.response.LoginResponse;
import com.ecommerce.fauzi.model.Role;
import com.ecommerce.fauzi.model.User;
import com.ecommerce.fauzi.repository.JpaAuthRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final JpaAuthRepository repository;
    private final PasswordEncoder encoder;

    public void register (RegisterRequest request){
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email is already in use");
        }
        String hashPassword = hash(request.getPassword());
        UUID userId = UUID.randomUUID();
        Role role = Role.ROLE_USER;

        User user = new User();
        user.setId(userId);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword);
        user.setRole(role);
        repository.save(user);
    }

    public LoginResponse login (LoginRequest request){
        User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        if (!verify(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }

        String token = generateToken(user.getId(), user.getEmail(), user.getRole().toString());

        return new LoginResponse(user.getName(), token);
    }

    private String hash(String password){
        return encoder.encode(password);
    }

    private boolean verify(String password, String hash){
        return encoder.matches(password, hash);
    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private String generateToken(UUID id, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 900 * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }
}
