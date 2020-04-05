package org.postman.CalendarSlotBookingservice.controller;

import com.sun.istack.Nullable;
import org.apache.coyote.Response;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.resource.EndPoints;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(EndPoints.APPOINTMENT_BASE)
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(path = EndPoints.APPOINTMENT_ALL, method = RequestMethod.GET)
    ResponseEntity findAll() {
        return appointmentService.findAll();
    }

    @PostMapping(path = EndPoints.APPOINTMENT_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@Valid @RequestBody Appointment appointment) throws ResourceNotFoundException {
        return appointmentService.create(appointment);
    }

    @GetMapping(path = EndPoints.APPOINTMENT_BY_ID)
    public ResponseEntity findById(@PathVariable Long appointmentId) {
        return appointmentService.findById(appointmentId);
    }

    @GetMapping(path = EndPoints.APPOINTMENT_BETWEEN_DATES)
    public ResponseEntity findByDateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                                            @Nullable @RequestParam("status") String status) {

        return appointmentService.findByDateRangeWithStatus(startDate, endDate,status);
    }



    @PutMapping(path = EndPoints.APPOINTMENT_UPDATE)
    public ResponseEntity update(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.update(appointmentId, appointment);
    }

    @PatchMapping(path = EndPoints.APPOINTMENT_BOOK)
    public ResponseEntity bookAppointment(@PathVariable Long appointmentId) {
        return appointmentService.bookAppointment(appointmentId);
    }

    @PatchMapping(path = EndPoints.APPOINTMENT_CANCEL)
    public ResponseEntity cancelAppointment(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    @DeleteMapping( path = EndPoints.APPOINTMENT_DELETE)
    public ResponseEntity deleteById(@PathVariable Long appointmentId) {

        return appointmentService.deleteById(appointmentId);
    }

    @GetMapping(path = EndPoints.APPOINTMENT_AFTER_DATES)
    public ResponseEntity findByDateAfter(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                          @Nullable @RequestParam("status") String status) {

        return appointmentService.findAllByAppointmentDateAfter(date,status);
    }

    @GetMapping(path = EndPoints.APPOINTMENT_BEFORE_DATES)
    public ResponseEntity findAllByDateBefore(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                                        @Nullable @RequestParam("status") String status) {

        return appointmentService.findAllByAppointmentDateBefore(date,status);
    }
}