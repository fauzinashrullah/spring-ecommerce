package com.ecommerce.fauzi.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.fauzi.dto.request.LoginRequest;
import com.ecommerce.fauzi.dto.request.RegisterRequest;
import com.ecommerce.fauzi.dto.response.LoginResponse;
import com.ecommerce.fauzi.exception.AlreadyExistException;
import com.ecommerce.fauzi.exception.UnauthorizedException;
import com.ecommerce.fauzi.model.Role;
import com.ecommerce.fauzi.model.User;
import com.ecommerce.fauzi.repository.JpaAuthRepository;
import com.ecommerce.fauzi.security.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JpaAuthRepository repository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public void register (RegisterRequest request){
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new AlreadyExistException("Email is already in use");
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
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        
        if (!verify(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getRole().toString());

        return new LoginResponse(user.getName(), token);
    }

    private String hash(String password){
        return encoder.encode(password);
    }

    private boolean verify(String password, String hash){
        return encoder.matches(password, hash);
    }

    
}
