package com.flowright.member_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.MemberSpecialization;

@Repository
public interface MemberSpecializationRepository extends JpaRepository<MemberSpecialization, Long> {
    // Define any custom query methods if needed
}
