package com.flowright.member_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.member_service.dto.MembersSpecializationDTO.AddSpecializationToMemberRequest;
import com.flowright.member_service.entity.MemberSpecialization;
import com.flowright.member_service.exception.MemberException;
import com.flowright.member_service.repository.MemberSpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSpecializationService {
    @Autowired
    private final MemberSpecializationRepository memberSpecializationRepository;

    public String addSpecializationToMember(AddSpecializationToMemberRequest request, UUID memberId) {
        // checks to see if the user already has this additional specialization
        if (memberSpecializationRepository
                .findByMemberIdAndSpecializationId(memberId, request.getSpecializationId())
                .isPresent()) {
            throw new MemberException("Specialization already exists for this member", HttpStatus.BAD_REQUEST);
        }

        if (request.getIsDefault() == true) {
            if (memberSpecializationRepository
                    .findByMemberIdAndIsDefault(memberId, true)
                    .isPresent()) {
                return "You cannot create another default specialization because there is already another default specialization";
            }
        }

        memberSpecializationRepository.save(MemberSpecialization.builder()
                .memberId(memberId)
                .specializationId(request.getSpecializationId())
                .level(request.getLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .isDefault(request.getIsDefault())
                .build());

        return "Specialization added successfully";
    }
}
