package org.postman.CalendarSlotBookingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.AppointmentStatus;
import org.postman.CalendarSlotBookingservice.model.BatchAppointment;
import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.validator.AppointmentStatusUpdater;
import org.postman.CalendarSlotBookingservice.validator.AppointmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    AppointmentValidator appointmentValidator;


    @Override
    public ResponseEntity findById(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if(!appointment.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CustomMessage(StringResoures.NOT_PRESENT,HttpStatus.NO_CONTENT));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointment.get()));
    }

    @Override
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(StringResoures.OPERATION_SUCCESSFUL
                ,HttpStatus.OK,appointmentRepository.findAll()));
    }

    @Override
    public ResponseEntity create(Appointment appointment)  {

        CustomMessage message = appointmentValidator.validateAndCreate(appointment);
        logger.info("Appointment Created : "+message);
        return ResponseEntity.status(message.getStatus()).body(message);
    }


    @Override
    public ResponseEntity update(Long appointmentId, Appointment appointment) {
        return appointmentValidator.validateAndUpdateAppointment(appointmentId,appointment);
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
            return ResponseEntity.ok().body(customMessage);
        }

        customMessage = new CustomMessage(StringResoures.APPOINTMENT_NOT_PRESENT, HttpStatus.NO_CONTENT);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
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
                logger.info("findByDateRangeWithStatus : "+startDate+" "+endDate+" "+status);
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

    @Override
    public ResponseEntity findAllByAppointmentDateAfter(LocalDate startDate, String status) {

        List<Appointment> appointments;

        switch (status) {
            case "booked":
                appointments = appointmentRepository.findAllByAppointmentDateAfterAndAppointmentStatus(startDate,AppointmentStatus.Booked);
                break;
            case "available":
                appointments = appointmentRepository.findAllByAppointmentDateAfterAndAppointmentStatus(startDate,AppointmentStatus.Available);
                break;
            case "all":
                appointments = appointmentRepository.findAllByAppointmentDateAfter(startDate);
                break;
            default:
                logger.info("findAllByAppointmentDateAfter : "+startDate+" "+status);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    @Override
    public ResponseEntity findAllByAppointmentDateBefore(LocalDate startDate, String status) {

        List<Appointment> appointments;

        switch (status) {
            case "booked":
                appointments = appointmentRepository.findAllByAppointmentDateBeforeAndAppointmentStatus(startDate,AppointmentStatus.Booked);
                break;
            case "available":
                appointments = appointmentRepository.findAllByAppointmentDateBeforeAndAppointmentStatus(startDate,AppointmentStatus.Available);
                break;
            case "all":
                appointments = appointmentRepository.findAllByAppointmentDateBefore(startDate);
                break;
            default:
                logger.info("findAllByAppointmentDateBefore : "+startDate+" "+status);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    @Override
    public ResponseEntity findByUserAndDateRangeWithStatus(LocalDate startDate, LocalDate endDate, Long userId, String status) {

        List<Appointment> appointments;
        Optional<User> creator = userRepository.findById(userId);

        creator.orElseThrow(() -> new UsernameNotFoundException(StringResoures.USER_NOT_FOUND + " " + userId ));

        switch (status) {
            case "booked":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBetweenAndAppointmentStatus(creator.get(),startDate,endDate,AppointmentStatus.Booked);
                break;
            case "available":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBetweenAndAppointmentStatus(creator.get(),startDate,endDate,AppointmentStatus.Available);
                break;
            case "all":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBetween(creator.get(),startDate,endDate);
                break;
            default:
                logger.info("findAllByAppointmentDateBefore : "+startDate+" "+endDate+" "+status);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    @Override
    public ResponseEntity findAllByUserAndAppointmentDateAfter(LocalDate date, Long userId, String status) {

        List<Appointment> appointments;
        Optional<User> creator = userRepository.findById(userId);

        creator.orElseThrow(() -> new UsernameNotFoundException(StringResoures.USER_NOT_FOUND + " " + userId ));

        switch (status) {
            case "booked":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateAfterAndAppointmentStatus(creator.get(),date,AppointmentStatus.Booked);
                break;
            case "available":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateAfterAndAppointmentStatus(creator.get(),date,AppointmentStatus.Available);
                break;
            case "all":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateAfter(creator.get(),date);
                break;
            default:
                logger.info("findAllByAppointmentDateBefore : "+date+" "+status);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    @Override
    public ResponseEntity findAllByUserAndAppointmentDateBefore(LocalDate date, Long userId, String status) {

        List<Appointment> appointments;
        Optional<User> creator = userRepository.findById(userId);

        creator.orElseThrow(() -> new UsernameNotFoundException(StringResoures.USER_NOT_FOUND + " " + userId ));

        switch (status) {
            case "booked":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBeforeAndAppointmentStatus(creator.get(),date,AppointmentStatus.Booked);
                break;
            case "available":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBeforeAndAppointmentStatus(creator.get(),date,AppointmentStatus.Available);
                break;
            case "all":
                appointments = appointmentRepository.findAllByCreatorAndAppointmentDateBefore(creator.get(),date);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomMessage(StringResoures.INVALID_STATUS,HttpStatus.BAD_REQUEST));
        }

        CustomMessage customMessage=new CustomMessage(StringResoures.OPERATION_SUCCESSFUL,HttpStatus.OK,appointments);
        return ResponseEntity.status(customMessage.getStatus()).body(customMessage);
    }

    private CustomMessage batchCreator(Appointment appointment){
        return appointmentValidator.validateAndCreate(appointment);
    }

    @Override
    public ResponseEntity createBatchAppointment(BatchAppointment batchAppointment){

        ArrayList<CustomMessage> customMessage = new ArrayList<>();

        batchAppointment.getAppointmentSlots().forEach(appointmentSlot -> {

            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(batchAppointment.getAppointmentDate());
            appointment.setAppointmentStartTime(appointmentSlot.getAppointmentStartTime());
            appointment.setAppointmentEndTime(appointmentSlot.getAppointmentEndTime());

            customMessage.add(batchCreator(appointment));
        });

        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }
}