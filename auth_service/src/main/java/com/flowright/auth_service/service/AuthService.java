package com.flowright.auth_service.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flowright.auth_service.dto.AuthResponse;
import com.flowright.auth_service.dto.LoginRequest;
import com.flowright.auth_service.dto.RegisterRequest;
import com.flowright.auth_service.entity.User;
import com.flowright.auth_service.exception.AuthException;
import com.flowright.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("Username already exists", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .id(UUID.randomUUID()) // Generate UUID
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);
        String access_token = jwtService.generateToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);
        user.setRefresh_token(refresh_token);
        userRepository.save(user);

        return AuthResponse.builder()
                .access_token(access_token)
                .refresh_token(refresh_token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        String access_token = jwtService.generateToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);
        user.setRefresh_token(refresh_token);
        userRepository.save(user);

        return AuthResponse.builder()
                .access_token(access_token)
                .refresh_token(refresh_token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
