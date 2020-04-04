package org.postman.CalendarSlotBookingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.validator.AppointmentStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityServiceImpl securityService;

    @Autowired
    AppointmentStatusUpdater appointmentStatusUpdater;


    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(StringResoures.OPERATION_SUCCESSFUL
                ,HttpStatus.OK,appointmentRepository.findAll()));
    }

    @Override
    public Appointment create(Appointment appointment) throws ResourceNotFoundException {

        return userRepository.findByUsername(securityService.findLoggedInUsername()).map(user -> {
            appointment.setCreator(user);
            appointment.setAppointmentStatus(AppointmentStatus.Available);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new ResourceNotFoundException(" User not found"));

//        return appointmentRepository.save(appointment);
    }

    // ToDo - updation remaining
    @Override
    public Appointment update(Long appointmentId, Appointment appointment) {
        return appointmentRepository.save(appointment);
    }


    @Override
    public ResponseEntity deleteById(Long appointmentId) {

        Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);

        CustomMessage customMessage;

        if(appointmentList.isPresent()){

            if( appointmentList.get().getCreator().getUsername().equals(securityService.findLoggedInUsername()) ){
                 appointmentRepository.deleteById(appointmentId);
                 customMessage = new CustomMessage(StringResoures.DELETION_SUCCESSFUL, HttpStatus.OK);
                 return ResponseEntity.ok(customMessage);
            }
            customMessage = new CustomMessage(StringResoures.PERMISSION_DENIED, HttpStatus.OK);
            return ResponseEntity.badRequest().body(customMessage);
        }

        customMessage = new CustomMessage(StringResoures.APPOINTMENT_NOT_PRESENT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(customMessage);
    }

    @Override
    public ResponseEntity findByDateRangeWithStatus(LocalDate startDate, LocalDate endDate, String status) {

        AppointmentStatus appointmentStatus;
        List<Appointment> appointments;

        switch (status) {
            case "booked":
                appointmentStatus = AppointmentStatus.Booked;
                appointments = appointmentRepository.findAllByAppointmentDateBetweenAndAppointmentStatus(startDate,endDate,appointmentStatus);
                break;
            case "available":
                appointmentStatus = AppointmentStatus.Available;
                appointments = appointmentRepository.findAllByAppointmentDateBetweenAndAppointmentStatus(startDate,endDate,appointmentStatus);
                break;
            case "all":
                appointments = appointmentRepository.findAllByAppointmentDateBetween(startDate, endDate);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    @Override
    public ResponseEntity bookAppointment(Long appointmentId) {
        return appointmentStatusUpdater.validateAndBook(appointmentId);
    }

    @Override
    public ResponseEntity cancelAppointment(Long appointmentId) {
        return appointmentStatusUpdater.validateAndCancel(appointmentId);
    }
}