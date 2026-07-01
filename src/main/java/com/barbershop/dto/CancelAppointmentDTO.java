package com.barbershop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelAppointmentDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;
}
