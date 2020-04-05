package org.postman.CalendarSlotBookingservice.validator;

import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentValidator {

    @Autowired
    SecurityServiceImpl securityService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity validateAndCreate(Appointment appointment)  {

        CustomMessage customMessage;
        User creator =  userRepository.findByUsername(securityService.findLoggedInUsername()).get();

        long alreadyExisting =  appointmentRepository.findByOverlappingSlotCount(
                appointment.getAppointmentDate(),appointment.getAppointmentStartTime(),appointment.getAppointmentEndTime());


        if(appointment.getAppointmentDate().isBefore(LocalDate.now())){

            customMessage = new CustomMessage(StringResoures.DATE_IS_IN_PAST,HttpStatus.OK);

        }else if( appointment.getAppointmentStartTime().after(appointment.getAppointmentEndTime()) ||
                appointment.getAppointmentStartTime().equals(appointment.getAppointmentEndTime()) ){

            customMessage = new CustomMessage(StringResoures.START_TIME_GREATER,HttpStatus.OK);

        }else if(alreadyExisting > 0){

            customMessage = new CustomMessage(StringResoures.SLOT_TIME_FULL,HttpStatus.OK);

        }else {

            appointment.setCreator(creator);
            appointment.setAppointmentStatus(AppointmentStatus.Available);
            Appointment saved = appointmentRepository.save(appointment);

            customMessage = new CustomMessage(StringResoures.APPOINTMENT_CREATED,HttpStatus.OK,saved);
        }

        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    public ResponseEntity validateAndUpdateAppointment(Long appointmentId, Appointment appointment){

        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointmentId);
        CustomMessage customMessage;
        String loggedInUsername = securityService.findLoggedInUsername();

        if(existingAppointment.isPresent()){

            if(!existingAppointment.get().getAppointmentDate().isAfter(LocalDate.now())){

                customMessage = new CustomMessage(StringResoures.PAST_APPOINTMENT, HttpStatus.OK);

            }else if (! existingAppointment.get().getCreator().getUsername().equals(loggedInUsername) ) {

                customMessage = new CustomMessage(StringResoures.PERMISSION_DENIED, HttpStatus.UNAUTHORIZED);

            }else if(appointment.getAppointmentEndTime() == null ||
                    appointment.getAppointmentStartTime() == null || appointment.getAppointmentDate() ==null ){

                customMessage = new CustomMessage(StringResoures.VALUES_NOT_PRESENT, HttpStatus.OK);

            }else if(!appointment.getAppointmentDate().isAfter(LocalDate.now())){

                customMessage = new CustomMessage(StringResoures.DATE_IS_IN_PAST,HttpStatus.OK);

            }else {

                existingAppointment.get().setAppointmentDate(appointment.getAppointmentDate());
                existingAppointment.get().setAppointmentStartTime(appointment.getAppointmentStartTime());
                existingAppointment.get().setAppointmentEndTime(appointment.getAppointmentEndTime());

                Appointment updatedValue = appointmentRepository.save(existingAppointment.get());

                customMessage = new CustomMessage(StringResoures.APPOINTMENT_UPDATED,HttpStatus.OK,updatedValue);
            }

            return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                (new CustomMessage(StringResoures.APPOINTMENT_NOT_PRESENT,HttpStatus.NOT_FOUND));

    }

}