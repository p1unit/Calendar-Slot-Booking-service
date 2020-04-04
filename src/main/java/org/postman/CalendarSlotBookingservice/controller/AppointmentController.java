package org.postman.CalendarSlotBookingservice.controller;

import com.sun.istack.Nullable;
import org.apache.coyote.Response;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
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
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    ResponseEntity findAll() {

        return appointmentService.findAll();
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@Valid @RequestBody Appointment appointment) throws ResourceNotFoundException {
        return appointmentService.create(appointment);
    }

    @GetMapping(path = "/{appointmentId}")
    public Optional<Appointment> findById(@PathVariable Long appointmentId) {
        return appointmentService.findById(appointmentId);
    }

    @GetMapping(path = "")
    public ResponseEntity findByDateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                                            @Nullable @RequestParam("status") String status) {

        return appointmentService.findByDateRangeWithStatus(startDate, endDate,status);
    }



    @PutMapping(path = "/{appointmentId}")
    public Appointment update(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.update(appointmentId, appointment);
    }

    @PatchMapping(path = "/book/{appointmentId}")
    public ResponseEntity bookAppointment(@PathVariable Long appointmentId) {
        return appointmentService.bookAppointment(appointmentId);
    }

    @PatchMapping(path = "/cancel/{appointmentId}")
    public ResponseEntity cancelAppointment(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    @DeleteMapping( path = "/{appointmentId}")
    public ResponseEntity deleteById(@PathVariable Long appointmentId) {

        return appointmentService.deleteById(appointmentId);
    }


}