package com.flowright.member_service.entity;

import java.util.UUID;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members_specializations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSpecialization {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "specialization_id", nullable = false)
    private UUID specializationId;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;
}
