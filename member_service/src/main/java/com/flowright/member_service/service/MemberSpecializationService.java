// package com.flowright.member_service.service;

// import org.springframework.stereotype.Service;

// import com.flowright.member_service.dto.MemberSpecializationResponse;
// import com.flowright.member_service.entity.MemberSpecialization;
// import com.flowright.member_service.repository.MemberSpecializationRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class MemberSpecializationService {
//     private final MemberSpecializationRepository memberSpecializationRepository;

//     public MemberSpecializationResponse getMemberSpecialization(Long id) {
//         MemberSpecialization memberSpecialization = memberSpecializationRepository
//                 .findById(id)
//                 .orElseThrow(() -> new RuntimeException("Member specialization not found"));

//         return MemberSpecializationResponse.builder()
//                 .memberId(memberSpecialization.getMember().getId())
//                 .specializationId(memberSpecialization.getSpecialization().getId())
//                 .level(memberSpecialization.getLevel())
//                 .yearsOfExperience(memberSpecialization.getYearsOfExperience())
//                 .build();
//     }

//     public void addSpecializationToMember(
//             Long memberId, Long specializationId, String level, Integer yearsOfExperience) {
//         MemberSpecialization memberSpecialization = MemberSpecialization.builder()
//                 .member(Member.builder().id(memberId).build())
//                 .specialization(Specialization.builder().id(specializationId).build())
//                 .level(level)
//                 .yearsOfExperience(yearsOfExperience)
//                 .build();

//         memberSpecializationRepository.save(memberSpecialization);
//     }

//     public void removeSpecializationFromMember(Long id) {
//         if (!memberSpecializationRepository.existsById(id)) {
//             throw new RuntimeException("Member specialization not found");
//         }
//         memberSpecializationRepository.deleteById(id);
//     }

//     public void updateLevelAndYearsOfExperience(Long id, String level, Integer yearsOfExperience) {
//         MemberSpecialization memberSpecialization = memberSpecializationRepository
//                 .findById(id)
//                 .orElseThrow(() -> new RuntimeException("Member specialization not found"));

//         memberSpecialization.setLevel(level);
//         memberSpecialization.setYearsOfExperience(yearsOfExperience);
//         memberSpecializationRepository.save(memberSpecialization);
//     }
// }
