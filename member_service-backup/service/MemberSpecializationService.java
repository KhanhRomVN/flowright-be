package com.flowright.member_service.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flowright.member_service.dto.MembersSpecializationDTO.MemberSpecializationResponse;
import com.flowright.member_service.entity.Member;
import com.flowright.member_service.entity.MemberSpecialization;
import com.flowright.member_service.entity.Specialization;
import com.flowright.member_service.repository.MemberRepository;
import com.flowright.member_service.repository.MemberSpecializationRepository;
import com.flowright.member_service.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSpecializationService {
    private final MemberSpecializationRepository memberSpecializationRepository;
    private final MemberRepository memberRepository;
    private final SpecializationRepository specializationRepository;

    public void addSpecializationToMember(
            UUID memberId, UUID specializationId, String level, int yearsOfExperience, boolean isDefault) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Specialization specialization = specializationRepository
                .findById(specializationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization not found"));

        MemberSpecialization memberSpecialization = new MemberSpecialization();
        memberSpecialization.setMemberId(member.getId());
        memberSpecialization.setSpecializationId(specialization.getId());
        memberSpecialization.setLevel(level);
        memberSpecialization.setYearsOfExperience(yearsOfExperience);
        memberSpecialization.setIsDefault(isDefault);
        // Lưu MemberSpecialization
        memberSpecializationRepository.save(memberSpecialization);
    }

    public MemberSpecializationResponse getMemberSpecializationById(UUID memberSpecializationId) {
        MemberSpecialization memberSpecialization = memberSpecializationRepository
                .findById(memberSpecializationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MemberSpecialization not found"));

        return new MemberSpecializationResponse(
                memberSpecialization.getMemberId(),
                memberSpecialization.getSpecializationId(),
                memberSpecialization.getLevel(),
                memberSpecialization.getYearsOfExperience(),
                memberSpecialization.getIsDefault());
    }
}
