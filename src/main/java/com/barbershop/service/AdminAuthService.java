package com.barbershop.service;

import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.DashboardDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.entity.Admin;
import com.barbershop.exception.BadRequestException;
import com.barbershop.exception.ResourceNotFoundException;
import com.barbershop.repository.AdminRepository;
import com.barbershop.repository.UserRepository;
import com.barbershop.repository.BarberRepository;
import com.barbershop.repository.AppointmentRepository;
import com.barbershop.security.JwtTokenProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class AdminAuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        Admin admin = adminRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), admin.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(admin.getEmail(), "ADMIN");

        AuthResponseDTO response = new AuthResponseDTO();
        response.setSuccess(true);
        response.setMessage("Admin logged in successfully");
        response.setToken(token);

        return response;
    }

    public DashboardDTO getDashboardData() {
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalBarbers(barberRepository.count());
        dashboard.setTotalAppointments(appointmentRepository.count());
        dashboard.setCancelledAppointments(
                appointmentRepository.findAll()
                        .stream()
                        .filter(apt -> apt.getCancelled())
                        .count()
        );
        dashboard.setCompletedAppointments(
                appointmentRepository.findAll()
                        .stream()
                        .filter(apt -> apt.getIsCompleted())
                        .count()
        );
        return dashboard;
    }
}
