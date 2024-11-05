package com.flowright.member_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flowright.member_service.dto.SpecializationDTO.CreateSpecializationRequest;
import com.flowright.member_service.dto.SpecializationDTO.SpecializationResponse;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;

    public SpecializationResponse createSpecialization(CreateSpecializationRequest request, Long workspaceId) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving specialization", e);
        }
    }

    public void deleteSpecialization(Long id) {
        if (!specializationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization not found");
        }
        specializationRepository.deleteById(id);
    }
}
