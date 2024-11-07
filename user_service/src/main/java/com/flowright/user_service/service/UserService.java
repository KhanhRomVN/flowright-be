package com.flowright.user_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flowright.user_service.dto.UserDTO;
import com.flowright.user_service.entity.User;
import com.flowright.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO getCurrentUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
