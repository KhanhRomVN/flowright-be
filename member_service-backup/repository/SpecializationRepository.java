package com.flowright.member_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
    Optional<Specialization> findByName(String name);

    Optional<Specialization> findById(UUID id);
}
