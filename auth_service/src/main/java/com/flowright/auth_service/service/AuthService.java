package com.flowright.auth_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowright.auth_service.dto.AuthResponse;
import com.flowright.auth_service.dto.LoginRequest;
import com.flowright.auth_service.dto.RegisterRequest;
import com.flowright.auth_service.elasticsearch.UserDocument;
import com.flowright.auth_service.elasticsearch.UserDocumentRepository;
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
    private final UserDocumentRepository userDocumentRepository;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        validateNewUser(request);

        User user = createUser(request);
        user = userRepository.save(user);

        saveUserDocument(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefresh_token(refreshToken);
        userRepository.save(user);

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefresh_token(refreshToken);
        userRepository.save(user);

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    private void validateNewUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("Username already exists", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists", HttpStatus.CONFLICT);
        }
    }

    private User createUser(RegisterRequest request) {
        return User.builder()
                .id(UUID.randomUUID())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private void saveUserDocument(User user) {
        UserDocument userDocument = new UserDocument(user.getId().toString(), user.getUsername(), user.getEmail());
        userDocumentRepository.save(userDocument);
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public List<UserDocument> searchUsers(String query) {
        return userDocumentRepository.findByUsernameContaining(query);
    }
}
