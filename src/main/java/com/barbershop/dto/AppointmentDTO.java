package com.barbershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long userId;
    private Long barberId;
    private String slotDate;
    private String slotTime;
    private String userData;
    private String barberData;
    private Boolean cancelled;
    private Boolean isCompleted;
    private String message;
    private LocalDateTime createdAt;
}
