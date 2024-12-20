package com.example.appointmentbooking.controller;

import com.example.appointmentbooking.model.Appointment;
import com.example.appointmentbooking.model.TimeSlot;
import com.example.appointmentbooking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/slots")
    public ResponseEntity<List<TimeSlot>> getAvailableSlots() {
        return ResponseEntity.ok(appointmentService.getAvailableSlots());
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestParam Long timeSlotId,
                                                         @RequestParam String userEmail) {
        return ResponseEntity.ok(appointmentService.createAppointment(timeSlotId, userEmail));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getUserAppointments(@RequestParam String userEmail) {
        return ResponseEntity.ok(appointmentService.getUserAppointments(userEmail));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id,
                                                         @RequestParam String userEmail) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id, userEmail));
    }
}