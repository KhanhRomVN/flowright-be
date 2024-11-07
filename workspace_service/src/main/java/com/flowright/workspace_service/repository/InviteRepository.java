package com.flowright.workspace_service.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowright.workspace_service.entity.Invite;

@Repository
public interface InviteRepository extends JpaRepository<Invite, UUID> {
    Optional<Invite> findByEmailAndOtp(String email, String otp);
}
