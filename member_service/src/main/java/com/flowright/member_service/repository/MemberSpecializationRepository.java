package com.flowright.member_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.MemberSpecialization;

@Repository
public interface MemberSpecializationRepository extends JpaRepository<MemberSpecialization, UUID> {
    Optional<MemberSpecialization> findByMemberIdAndSpecializationId(UUID memberId, UUID specializationId);

    Optional<MemberSpecialization> findByMemberIdAndIsDefault(UUID memberId, Boolean isDefault);

    List<MemberSpecialization> findBySpecializationId(UUID specializationId);
}
