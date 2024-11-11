package com.flowright.member_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.SpecializationDTO.CreateSpecializationRequest;
import com.flowright.member_service.dto.SpecializationDTO.SpecializationResponse;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.exception.MemberException;
import com.flowright.member_service.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;

    public SpecializationResponse createSpecialization(CreateSpecializationRequest request, UUID workspaceId) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new MemberException("Name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Specialization specialization = Specialization.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .build();

        try {
            Specialization savedSpecialization = specializationRepository.save(specialization);
            return SpecializationResponse.builder()
                    .id(savedSpecialization.getId())
                    .name(savedSpecialization.getName())
                    .description(savedSpecialization.getDescription())
                    .workspaceId(savedSpecialization.getWorkspaceId())
                    .build();
        } catch (Exception e) {
            throw new MemberException("Error saving specialization", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteSpecialization(UUID id) {
        if (!specializationRepository.existsById(id)) {
            throw new MemberException("Specialization not found", HttpStatus.NOT_FOUND);
        }
        specializationRepository.deleteById(id);
    }

    public List<SpecializationResponse> getAllSpecializations(UUID workspaceId) {
        return specializationRepository.findByWorkspaceId(workspaceId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private SpecializationResponse convertToResponse(Specialization specialization) {
        return SpecializationResponse.builder()
                .id(specialization.getId())
                .name(specialization.getName())
                .description(specialization.getDescription())
                .workspaceId(specialization.getWorkspaceId())
                .build();
    }
}
