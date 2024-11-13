package com.flowright.task_service.entity;

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
@Table(name = "task_assignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "team_id")
    private UUID teamId;
}
