package com.openschool.hw.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
