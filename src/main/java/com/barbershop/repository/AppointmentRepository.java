package com.barbershop.repository;

import com.barbershop.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByBarberId(Long barberId);
    List<Appointment> findBySlotDate(String slotDate);
}
