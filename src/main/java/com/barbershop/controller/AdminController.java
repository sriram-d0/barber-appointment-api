package com.barbershop.controller;

import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.BarberRegistrationDTO;
import com.barbershop.dto.BarberDTO;
import com.barbershop.dto.DashboardDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.dto.AppointmentDTO;
import com.barbershop.dto.CancelAppointmentDTO;
import com.barbershop.service.AdminAuthService;
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
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private BarberAuthService barberAuthService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        AuthResponseDTO response = adminAuthService.login(loginDTO);
        return ResponseUtil.success(response, "Admin logged in successfully", HttpStatus.OK);
    }

    @PostMapping("/barber")
    public ResponseEntity<?> addBarber(
            @Valid @RequestPart("barber") BarberRegistrationDTO barberDTO,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        String fileName = imageFile.getOriginalFilename();
        BarberDTO savedBarber = barberAuthService.registerBarber(barberDTO, imageData, fileName);
        return ResponseUtil.success(savedBarber, "Barber added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/barbers")
    public ResponseEntity<?> getAllBarbers() {
        List<BarberDTO> barbers = barberAuthService.getAllBarbers();
        return ResponseUtil.success(barbers, "Barbers retrieved", HttpStatus.OK);
    }

    @DeleteMapping("/barber/{id}")
    public ResponseEntity<?> deleteBarber(@PathVariable Long id) {
        barberAuthService.deleteBarber(id);
        return ResponseUtil.success("Barber deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseUtil.success(appointments, "All appointments retrieved", HttpStatus.OK);
    }

    @PostMapping("/appointment/cancel")
    public ResponseEntity<?> cancelAppointment(@Valid @RequestBody CancelAppointmentDTO cancelDTO) {
        appointmentService.cancelAppointment(cancelDTO);
        return ResponseUtil.success("Appointment cancelled successfully", HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData() {
        DashboardDTO dashboard = adminAuthService.getDashboardData();
        return ResponseUtil.success(dashboard, "Dashboard data retrieved", HttpStatus.OK);
    }
}
