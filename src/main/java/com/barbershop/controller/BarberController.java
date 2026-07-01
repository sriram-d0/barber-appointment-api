package com.barbershop.controller;

import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.BarberRegistrationDTO;
import com.barbershop.dto.BarberDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.dto.AppointmentDTO;
import com.barbershop.service.BarberAuthService;
import com.barbershop.service.AppointmentService;
import com.barbershop.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/barber")
@CrossOrigin(origins = "*")
public class BarberController {

    @Autowired
    private BarberAuthService barberAuthService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        AuthResponseDTO response = barberAuthService.login(loginDTO);
        return ResponseUtil.success(response, "Logged in successfully", HttpStatus.OK);
    }

    @GetMapping("/appointments/{barberId}")
    public ResponseEntity<?> getBarberAppointments(@PathVariable Long barberId) {
        List<AppointmentDTO> appointments = appointmentService.getBarberAppointments(barberId);
        return ResponseUtil.success(appointments, "Barber appointments retrieved", HttpStatus.OK);
    }
}
