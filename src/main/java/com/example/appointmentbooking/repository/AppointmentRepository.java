package com.example.appointmentbooking.repository;

import com.example.appointmentbooking.model.Appointment;
import com.example.appointmentbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
}