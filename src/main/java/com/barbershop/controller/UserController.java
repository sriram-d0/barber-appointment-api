package com.barbershop.controller;

import com.barbershop.dto.UserRegistrationDTO;
import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.dto.UserProfileDTO;
import com.barbershop.dto.BookAppointmentDTO;
import com.barbershop.dto.CancelAppointmentDTO;
import com.barbershop.dto.AppointmentDTO;
import com.barbershop.service.UserAuthService;
import com.barbershop.service.AppointmentService;
import com.barbershop.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        AuthResponseDTO response = userAuthService.register(registrationDTO);
        return ResponseUtil.success(response, "User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        AuthResponseDTO response = userAuthService.login(loginDTO);
        return ResponseUtil.success(response, "Logged in successfully", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        // In a real app, you'd query by email. For simplicity, we'll extract from token
        // This is a simplified version - you may need to adjust based on your token structure
        return ResponseUtil.success("Profile retrieved", HttpStatus.OK);
    }

    @PostMapping("/book-appointment")
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody BookAppointmentDTO bookDTO) {
        AppointmentDTO appointment = appointmentService.bookAppointment(bookDTO);
        return ResponseUtil.success(appointment, "Appointment booked successfully", HttpStatus.CREATED);
    }

    @PostMapping("/cancel-appointment")
    public ResponseEntity<?> cancelAppointment(@Valid @RequestBody CancelAppointmentDTO cancelDTO) {
        appointmentService.cancelAppointment(cancelDTO);
        return ResponseUtil.success("Appointment cancelled successfully", HttpStatus.OK);
    }

    @GetMapping("/appointments/{userId}")
    public ResponseEntity<?> getUserAppointments(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = appointmentService.getUserAppointments(userId);
        return ResponseUtil.success(appointments, "Appointments retrieved", HttpStatus.OK);
    }
}
