package com.flowright.member_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTO
import com.flowright.member_service.dto.SpecializationDTO.CreateSpecializationRequest;
import com.flowright.member_service.dto.SpecializationDTO.SpecializationResponse;

// Service
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
            Long workspaceId = jwtService.extractWorkspaceId(token);

            SpecializationResponse response = specializationService.createSpecialization(request, workspaceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log the exception and return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete a specialization: DELETE /member-service/specializations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(
            @PathVariable Long id, @RequestHeader("access_token") String token) {
        try {
            jwtService.validateToken(token);
            specializationService.deleteSpecialization(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the exception and return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
