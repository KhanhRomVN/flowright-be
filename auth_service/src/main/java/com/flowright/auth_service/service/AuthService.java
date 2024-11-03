package com.flowright.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flowright.auth_service.dto.AuthResponse;
import com.flowright.auth_service.dto.LoginRequest;
import com.flowright.auth_service.dto.RegisterRequest;
import com.flowright.auth_service.entity.User;
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
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
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
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String access_token = jwtService.generateToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);
        user.setRefresh_token(refresh_token);
        userRepository.save(user);

        return AuthResponse.builder()
                .access_token(access_token)
                .refresh_token(refresh_token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
