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
@Table(name = "task_comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskComment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
