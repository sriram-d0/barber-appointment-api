package com.barbershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberDTO {
    private Long id;
    private String name;
    private String email;
    private String image;
    private String about;
    private Boolean available;
    private String slotsBooked;
}
