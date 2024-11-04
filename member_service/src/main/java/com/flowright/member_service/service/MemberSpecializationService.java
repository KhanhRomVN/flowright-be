package com.flowright.member_service.service;

import org.springframework.stereotype.Service;

import com.flowright.member_service.entity.MemberSpecialization;
import com.flowright.member_service.repository.MemberSpecializationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSpecializationService {
    private final MemberSpecializationRepository memberSpecializationRepository;

    public void addSpecializationToMember(Long memberId, Long specializationId) {
        MemberSpecialization memberSpecialization = new MemberSpecialization();
        memberSpecialization.setMemberId(memberId);
        memberSpecialization.setSpecializationId(specializationId);
        memberSpecializationRepository.save(memberSpecialization);
    }
}
