package com.flowright.member_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.MemberSpecialization;

@Repository
public interface MemberSpecializationRepository extends JpaRepository<MemberSpecialization, Long> {
    List<MemberSpecialization> findByMemberId(Long memberId);

    @Query(
            "SELECT ms FROM MemberSpecialization ms WHERE ms.specialization.id = :specializationId AND ms.isDefault = :isDefault")
    List<MemberSpecialization> findBySpecializationId(Long specializationId, boolean isDefault);
}
