package org.postman.CalendarSlotBookingservice.service;

import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    ResponseEntity findById(Long appointmentId);

    ResponseEntity findAll();

    Appointment create(Appointment appointment) throws ResourceNotFoundException;

    ResponseEntity update(Long appointmentId, Appointment appointment);

    ResponseEntity deleteById(Long appointmentId);

    ResponseEntity findByDateRangeWithStatus(LocalDate startDate, LocalDate endDate,String status);

    ResponseEntity bookAppointment(Long appointmentId);

    ResponseEntity cancelAppointment(Long appointmentId);

    ResponseEntity findAllByAppointmentDateAfter(LocalDate startDate, String status);

    ResponseEntity findAllByAppointmentDateBefore(LocalDate startDate, String status);

    ResponseEntity findByUserAndDateRangeWithStatus(LocalDate startDate, LocalDate endDate, Long userId, String status);

    ResponseEntity findAllByUserAndAppointmentDateAfter(LocalDate date, Long userId, String status);

    ResponseEntity findAllByUserAndAppointmentDateBefore(LocalDate date, Long userId, String status);
}

