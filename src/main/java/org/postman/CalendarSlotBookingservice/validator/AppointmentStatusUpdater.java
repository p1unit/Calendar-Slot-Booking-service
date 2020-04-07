package org.postman.CalendarSlotBookingservice.validator;

import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.service.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Component
public class AppointmentStatusUpdater {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentStatusUpdater.class);

    @Autowired
    SecurityServiceImpl securityService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity validateAndCancel( Long appointmentId ){

        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointmentId);
        String loggedInUsername = securityService.findLoggedInUsername();
        CustomMessage customMessage;

        if (existingAppointment.isPresent()) {

            if(!existingAppointment.get().getAppointmentDate().isAfter(LocalDate.now())){

                customMessage = new CustomMessage(StringResoures.PAST_APPOINTMENT,HttpStatus.OK);

            } else if (existingAppointment.get().getAppointmentStatus() == AppointmentStatus.Available) {

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_IS_SAME, HttpStatus.OK);

            }else if (! existingAppointment.get().getCreator().getUsername().equals(loggedInUsername)
                    && ! existingAppointment.get().getBookerEmail().equals(loggedInUsername)) {

                customMessage = new CustomMessage(StringResoures.PERMISSION_DENIED, HttpStatus.OK);

            }else {

                existingAppointment.get().setAppointmentStatus(AppointmentStatus.Available);

                existingAppointment.get().setBookedBy(null);
                existingAppointment.get().setBookerEmail(null);
                Appointment savedValue = appointmentRepository.save(existingAppointment.get());

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_CANCELED,HttpStatus.OK,savedValue);
            }

            return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
        }

        logger.info("validateAndCancel : "+StringResoures.APPOINTMENT_NOT_PRESENT+" "+appointmentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body
                (new CustomMessage(StringResoures.APPOINTMENT_NOT_PRESENT,HttpStatus.NO_CONTENT));
    }




    public ResponseEntity validateAndBook( Long appointmentId ){

        CustomMessage customMessage;

        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointmentId);
        String loggedInUsername = securityService.findLoggedInUsername();

        if (existingAppointment.isPresent()) {

            if(!existingAppointment.get().getAppointmentDate().isAfter(LocalDate.now())){

                customMessage = new CustomMessage(StringResoures.PAST_APPOINTMENT,HttpStatus.OK);

            } else if (existingAppointment.get().getCreator().getUsername().equals(loggedInUsername) ) {

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_USER_SAME, HttpStatus.OK);

            } else if (existingAppointment.get().getAppointmentStatus() == AppointmentStatus.Booked) {

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_IS_SAME, HttpStatus.OK);

            } else {

                existingAppointment.get().setAppointmentStatus(AppointmentStatus.Booked);

                Optional<User> loggeinUser = userRepository.findByUsername(loggedInUsername);
                existingAppointment.get().setBookedBy(loggeinUser.get().getName());
                existingAppointment.get().setBookerEmail(loggeinUser.get().getUsername());

                Appointment savedValue = appointmentRepository.save(existingAppointment.get());

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_BOOKED,HttpStatus.OK,savedValue);
            }

            return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
        }

        logger.info("validateAndBook : "+StringResoures.APPOINTMENT_NOT_PRESENT+" "+appointmentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body
                (new CustomMessage(StringResoures.APPOINTMENT_NOT_PRESENT,HttpStatus.NO_CONTENT));

    }

}