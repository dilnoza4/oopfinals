package com.example.appointmentbooking.repository;

import com.example.appointmentbooking.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByStartTimeGreaterThanAndAvailableTrue(LocalDateTime startTime);
}