package org.postman.CalendarSlotBookingservice.controller;

import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    List<Appointment> findAll() {

        return appointmentService.findAll();
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@Valid @RequestBody Appointment appointment) throws ResourceNotFoundException {
        return appointmentService.create(appointment);
    }
}