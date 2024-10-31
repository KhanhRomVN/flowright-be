package com.flowright.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.user_service.dto.UserDTO;
import com.flowright.user_service.service.JwtService;
import com.flowright.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader("access_token") String token) {
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(userService.getCurrentUser(userId));
    }
}
