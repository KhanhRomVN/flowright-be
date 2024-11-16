package com.flowright.task_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskLog {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID taskId;
    private String logTitle;
    private String logDescription;
    private LocalDateTime logDate;
}
