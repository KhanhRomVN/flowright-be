package com.flowright.auth_service.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.auth_service.dto.AuthResponse;
import com.flowright.auth_service.dto.LoginRequest;
import com.flowright.auth_service.dto.RegisterRequest;
import com.flowright.auth_service.service.AuthService;
import com.flowright.auth_service.service.kafka.MessageProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MessageProducer messageProducer;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/kafka/test")
    public ResponseEntity<String> testKafka(@RequestBody String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka: " + message);
    }
}
