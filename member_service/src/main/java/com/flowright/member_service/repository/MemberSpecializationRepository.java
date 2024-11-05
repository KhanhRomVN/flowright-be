package com.flowright.member_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.MemberSpecialization;

@Repository
public interface MemberSpecializationRepository extends JpaRepository<MemberSpecialization, Long> {
    List<MemberSpecialization> findByMemberId(Long memberId);

    List<MemberSpecialization> findBySpecializationId(Long specializationId);
}
