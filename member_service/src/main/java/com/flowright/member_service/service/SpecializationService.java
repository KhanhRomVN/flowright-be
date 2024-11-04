package com.flowright.member_service.service;

import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.CreateSpecializationRequest;
import com.flowright.member_service.dto.SpecializationResponse;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;

    public SpecializationResponse createSpecialization(CreateSpecializationRequest request, Long workspaceId) {
        Specialization specialization = Specialization.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(workspaceId)
                .build();

        Specialization savedSpecialization = specializationRepository.save(specialization);

        return SpecializationResponse.builder()
                .id(savedSpecialization.getId())
                .name(savedSpecialization.getName())
                .description(savedSpecialization.getDescription())
                .workspaceId(savedSpecialization.getWorkspaceId())
                .build();
    }

    public void deleteSpecialization(Long id) {
        specializationRepository.deleteById(id);
    }
}
