package com.barbershop.service;

import com.barbershop.dto.LoginRequestDTO;
import com.barbershop.dto.BarberRegistrationDTO;
import com.barbershop.dto.BarberDTO;
import com.barbershop.dto.AuthResponseDTO;
import com.barbershop.entity.Barber;
import com.barbershop.exception.BadRequestException;
import com.barbershop.exception.ResourceNotFoundException;
import com.barbershop.repository.BarberRepository;
import com.barbershop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarberAuthService {

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public BarberDTO registerBarber(BarberRegistrationDTO registrationDTO, byte[] imageData, String fileName) {
        if (barberRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new BadRequestException("Email already registered");
        }


        Barber barber = new Barber();
        barber.setName(registrationDTO.getName());
        barber.setEmail(registrationDTO.getEmail());
        barber.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        barber.setAbout(registrationDTO.getAbout());
        barber.setAvailable(true);
        barber.setSlotsBooked("{}");

        Barber savedBarber = barberRepository.save(barber);
        return convertToDTO(savedBarber);
    }

    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        Barber barber = barberRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), barber.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(barber.getEmail(), "BARBER");

        AuthResponseDTO response = new AuthResponseDTO();
        response.setSuccess(true);
        response.setMessage("Logged in successfully");
        response.setToken(token);

        return response;
    }

    public List<BarberDTO> getAllBarbers() {
        return barberRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BarberDTO getBarberById(Long barberId) {
        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));
        return convertToDTO(barber);
    }

    public void deleteBarber(Long barberId) {
        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));
        barberRepository.delete(barber);
    }

    private BarberDTO convertToDTO(Barber barber) {
        BarberDTO dto = new BarberDTO();
        dto.setId(barber.getId());
        dto.setName(barber.getName());
        dto.setEmail(barber.getEmail());
        dto.setImage(barber.getImage());
        dto.setAbout(barber.getAbout());
        dto.setAvailable(barber.getAvailable());
        dto.setSlotsBooked(barber.getSlotsBooked());
        return dto;
    }
}
