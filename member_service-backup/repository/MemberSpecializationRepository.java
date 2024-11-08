package com.flowright.member_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.MemberSpecialization;

@Repository
public interface MemberSpecializationRepository extends JpaRepository<MemberSpecialization, UUID> {
    List<MemberSpecialization> findByMemberId(UUID memberId);

    @Query(
            "SELECT ms FROM MemberSpecialization ms WHERE ms.specialization.id = :specializationId AND ms.isDefault = :isDefault")
    List<MemberSpecialization> findBySpecializationId(UUID specializationId, boolean isDefault);
}
