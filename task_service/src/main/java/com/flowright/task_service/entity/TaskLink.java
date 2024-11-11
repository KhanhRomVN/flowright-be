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
@Table(name = "task_links")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskLink {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;
}
