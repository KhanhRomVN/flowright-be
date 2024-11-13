package com.flowright.project_service.entity;

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
@Table(name = "project_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLog {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "log_title", nullable = false)
    private String logTitle;

    @Column(name = "log_description", nullable = false)
    private String logDescription;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate;
}
