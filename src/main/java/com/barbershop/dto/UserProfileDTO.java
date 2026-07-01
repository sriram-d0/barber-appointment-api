package com.barbershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String userName;
    private String email;
    private String phone;
    private String gender;
    private String dob;
    private String image;
}
