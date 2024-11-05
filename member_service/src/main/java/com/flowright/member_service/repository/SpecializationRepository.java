package com.flowright.member_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.member_service.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);

    Optional<Specialization> findById(Long id);
}
