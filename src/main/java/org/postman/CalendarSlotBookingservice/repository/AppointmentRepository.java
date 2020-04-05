package org.postman.CalendarSlotBookingservice.repository;

import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {


    List<Appointment> findAllByAppointmentDateAfterAndAppointmentStatus(@Param("date") LocalDate date,
                                                                        @Param("appointmentStatus")AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateAfter(@Param("date") LocalDate date);

    List<Appointment> findAllByAppointmentDateBeforeAndAppointmentStatus(@Param("date") LocalDate date,
                                                                         @Param("appointmentStatus")AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateBefore(@Param("date") LocalDate date);

//    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startDate AND a.appointmentDate <= :endDate")
    List<Appointment> findAllByAppointmentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Appointment> findById(Long id);

    List<Appointment> findAllByAppointmentDateBetweenAndAppointmentStatus(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                                                          @Param("appointmentStatus")AppointmentStatus appointmentStatus);


}
