package com.flowright.member_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.member_service.dto.SpecializationDTO.CreateSpecializationRequest;
import com.flowright.member_service.dto.SpecializationDTO.SpecializationResponse;
import com.flowright.member_service.service.JwtService;
import com.flowright.member_service.service.SpecializationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-service/specializations")
@RequiredArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;
    private final JwtService jwtService;

    // Create a new specialization: POST /member-service/specializations
    @PostMapping
    public ResponseEntity<SpecializationResponse> createSpecialization(
            @RequestBody CreateSpecializationRequest request, @RequestHeader("access_token") String token) {
        try {
            UUID workspaceId = jwtService.extractWorkspaceId(token);

            SpecializationResponse response = specializationService.createSpecialization(request, workspaceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log the exception and return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
