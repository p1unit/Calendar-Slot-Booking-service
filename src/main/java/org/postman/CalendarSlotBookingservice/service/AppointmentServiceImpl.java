package org.postman.CalendarSlotBookingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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


    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findAllByAppointmentDateBetween(startDate, endDate);
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

    @Override
    public Appointment update(Long appointmentId, Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateStatus(Long appointmentId, Appointment appointment) {

        // ToDo - manage it

        Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);

        if(appointmentList.isPresent()){

            if(appointmentList.get().getCreator().getUsername().equals(securityService.findLoggedInUsername())
                    || appointment.getAppointmentStatus() != appointment.getAppointmentStatus()){
                appointmentList.get().setAppointmentStatus(appointment.getAppointmentStatus());
            }
            return appointmentRepository.save(appointmentList.get());
        }

        return null;
    }

    @Override
    public ResponseEntity<CustomMessage> deleteById(Long appointmentId) {

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
}