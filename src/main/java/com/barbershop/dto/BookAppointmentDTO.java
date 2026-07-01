package com.barbershop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAppointmentDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Barber ID is required")
    private Long barberId;

    @NotBlank(message = "Slot date is required")
    private String slotDate;

    @NotBlank(message = "Slot time is required")
    private String slotTime;

    private String message;
}
