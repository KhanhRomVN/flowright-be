package com.flowright.task_service.entity;

import java.time.LocalDateTime;
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
@Table(name = "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "priority")
    private String priority;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "previous_task_id")
    private UUID previousTaskId;

    @Column(name = "next_task_id")
    private UUID nextTaskId;

    @Column(name = "task_group_id")
    private UUID taskGroupId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
