package org.postman.CalendarSlotBookingservice.service;

import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Optional<Appointment> findById(Long appointmentId);

    List<Appointment> findAll();

    List<Appointment> findByDateRange(LocalDate startDate, LocalDate endDate);

    Appointment create(Appointment appointment) throws ResourceNotFoundException;

    Appointment update(Long appointmentId, Appointment appointment);

    Appointment updateStatus(Long appointmentId, Appointment appointment);

    void deleteById(Long appointmentId);

}

