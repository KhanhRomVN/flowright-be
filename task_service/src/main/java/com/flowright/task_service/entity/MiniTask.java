package com.flowright.task_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mini_tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID taskId;

    private String name;

    private String description;

    private String status;

    private UUID teamId;

    private UUID memberId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
