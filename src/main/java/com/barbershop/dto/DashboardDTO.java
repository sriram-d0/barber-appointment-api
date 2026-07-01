package com.barbershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalUsers;
    private Long totalBarbers;
    private Long totalAppointments;
    private Long cancelledAppointments;
    private Long completedAppointments;
}
