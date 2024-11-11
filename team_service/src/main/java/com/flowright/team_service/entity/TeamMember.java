package com.flowright.team_service.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_members")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "team_id", nullable = false)
    private UUID teamId;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;
}
