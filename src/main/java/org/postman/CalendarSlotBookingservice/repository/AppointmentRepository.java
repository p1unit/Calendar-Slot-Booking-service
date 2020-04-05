package org.postman.CalendarSlotBookingservice.repository;

import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    Optional<Appointment> findById(Long id);

    @Query("SELECT COUNT (a) from Appointment a where ( a.appointmentDate = :appointmentDate ) AND ( (a.appointmentStartTime >= :appointmentStartTime " +
            "AND a.appointmentStartTime < :appointmentEndTime ) OR ( a.appointmentEndTime > :appointmentStartTime AND a.appointmentEndTime <= :appointmentEndTime) )")

    Long findByOverlappingSlotCount(@Param("appointmentDate") LocalDate appointmentDate,
                                                 @Param("appointmentStartTime") Time appointmentStartTime,
                                                 @Param("appointmentEndTime") Time appointmentEndTime);

    //    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startDate AND a.appointmentDate <= :endDate")
    List<Appointment> findAllByAppointmentDateBetween( LocalDate startDate, LocalDate endDate);

    List<Appointment> findAllByAppointmentDateBetweenAndAppointmentStatus( LocalDate startDate, LocalDate endDate, AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateAfterAndAppointmentStatus( LocalDate date, AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateAfter( LocalDate date);

    List<Appointment> findAllByAppointmentDateBeforeAndAppointmentStatus( LocalDate date, AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateBefore( LocalDate date);

    List<Appointment> findAllByCreatorAndAppointmentDateBetween( User creator,  LocalDate startDate,  LocalDate endDate);

    List<Appointment> findAllByCreatorAndAppointmentDateBetweenAndAppointmentStatus( User creator,  LocalDate startDate, LocalDate endDate, AppointmentStatus status);

    List<Appointment> findAllByCreatorAndAppointmentDateBefore( User creator, LocalDate date);

    List<Appointment> findAllByCreatorAndAppointmentDateBeforeAndAppointmentStatus( User creator,  LocalDate date, AppointmentStatus status);

    List<Appointment> findAllByCreatorAndAppointmentDateAfter( User creator, LocalDate date);

    List<Appointment> findAllByCreatorAndAppointmentDateAfterAndAppointmentStatus( User creator, LocalDate date, AppointmentStatus status);
}
