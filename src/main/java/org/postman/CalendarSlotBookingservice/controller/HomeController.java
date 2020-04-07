package org.postman.CalendarSlotBookingservice.controller;

import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping(path = "")
    public CustomMessage home() {
        return new CustomMessage("Welcome in the Appointment booking system", HttpStatus.OK);
    }
}