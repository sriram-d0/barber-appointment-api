package com.barbershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long barberId;

    @Column(nullable = false)
    private String slotDate;

    @Column(nullable = false)
    private String slotTime;

    @Column(nullable = false, columnDefinition = "JSON")
    private String userData;

    @Column(nullable = false, columnDefinition = "JSON")
    private String barberData;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean cancelled;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCompleted;

    @Column(columnDefinition = "TEXT")
    private String message = "";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (cancelled == null) cancelled = false;
        if (isCompleted == null) isCompleted = false;
        if (message == null) message = "";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
