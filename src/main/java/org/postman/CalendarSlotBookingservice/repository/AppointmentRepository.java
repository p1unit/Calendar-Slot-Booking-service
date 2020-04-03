package org.postman.CalendarSlotBookingservice.repository;

import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByAppointmentDateAfter(LocalDate startDate);

    List<Appointment> findAllByAppointmentDateBetween(LocalDate startDate,LocalDate endDate);



}
