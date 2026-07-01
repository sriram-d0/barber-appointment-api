package com.barbershop.service;

import com.barbershop.dto.AppointmentDTO;
import com.barbershop.dto.BookAppointmentDTO;
import com.barbershop.dto.CancelAppointmentDTO;
import com.barbershop.entity.Appointment;
import com.barbershop.entity.User;
import com.barbershop.entity.Barber;
import com.barbershop.exception.BadRequestException;
import com.barbershop.exception.ResourceNotFoundException;
import com.barbershop.repository.AppointmentRepository;
import com.barbershop.repository.UserRepository;
import com.barbershop.repository.BarberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AppointmentDTO bookAppointment(BookAppointmentDTO bookDTO) {
        User user = userRepository.findById(bookDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Barber barber = barberRepository.findById(bookDTO.getBarberId())
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        if (!barber.getAvailable()) {
            throw new BadRequestException("Barber is not available");
        }

        Appointment appointment = new Appointment();
        appointment.setUserId(bookDTO.getUserId());
        appointment.setBarberId(bookDTO.getBarberId());
        appointment.setSlotDate(bookDTO.getSlotDate());
        appointment.setSlotTime(bookDTO.getSlotTime());
        appointment.setMessage(bookDTO.getMessage() != null ? bookDTO.getMessage() : "");

        try {
            appointment.setUserData(objectMapper.writeValueAsString(user));
            appointment.setBarberData(objectMapper.writeValueAsString(barber));
        } catch (Exception e) {
            throw new BadRequestException("Error creating appointment");
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    public void cancelAppointment(CancelAppointmentDTO cancelDTO) {
        Appointment appointment = appointmentRepository.findById(cancelDTO.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (!appointment.getUserId().equals(cancelDTO.getUserId())) {
            throw new BadRequestException("Unauthorized to cancel this appointment");
        }

        if (appointment.getCancelled()) {
            throw new BadRequestException("Appointment is already cancelled");
        }

        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentDTO> getUserAppointments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return appointmentRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getBarberAppointments(Long barberId) {
        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        return appointmentRepository.findByBarberId(barberId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setUserId(appointment.getUserId());
        dto.setBarberId(appointment.getBarberId());
        dto.setSlotDate(appointment.getSlotDate());
        dto.setSlotTime(appointment.getSlotTime());
        dto.setUserData(appointment.getUserData());
        dto.setBarberData(appointment.getBarberData());
        dto.setCancelled(appointment.getCancelled());
        dto.setIsCompleted(appointment.getIsCompleted());
        dto.setMessage(appointment.getMessage());
        dto.setCreatedAt(appointment.getCreatedAt());
        return dto;
    }
}
