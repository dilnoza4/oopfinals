package com.example.appointmentbooking.service;

import com.example.appointmentbooking.model.Appointment;
import com.example.appointmentbooking.model.TimeSlot;
import com.example.appointmentbooking.model.User;
import com.example.appointmentbooking.repository.AppointmentRepository;
import com.example.appointmentbooking.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;

    public List<TimeSlot> getAvailableSlots() {
        return timeSlotRepository.findByStartTimeGreaterThanAndAvailableTrue(LocalDateTime.now());
    }

    @Transactional
    public Appointment createAppointment(Long timeSlotId, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Time slot not found"));

        if (!timeSlot.isAvailable()) {
            throw new RuntimeException("Time slot is not available");
        }

        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setTimeSlot(timeSlot);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getUserAppointments(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        return appointmentRepository.findByUser(user);
    }

    @Transactional
    public Appointment cancelAppointment(Long appointmentId, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Appointment does not belong to user");
        }

        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELED) {
            throw new RuntimeException("Appointment is already canceled");
        }

        TimeSlot timeSlot = appointment.getTimeSlot();
        timeSlot.setAvailable(true);
        timeSlotRepository.save(timeSlot);

        appointment.setStatus(Appointment.AppointmentStatus.CANCELED);
        return appointmentRepository.save(appointment);
    }
}