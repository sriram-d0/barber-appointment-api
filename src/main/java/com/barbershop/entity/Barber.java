package com.barbershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "barbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String about;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean available;

    @Column(columnDefinition = "JSON")
    private String slotsBooked;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (available == null) available = true;
        if (slotsBooked == null) slotsBooked = "{}";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
