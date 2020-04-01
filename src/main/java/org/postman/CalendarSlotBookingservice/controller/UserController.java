package org.postman.CalendarSlotBookingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/temp")
@RestController
public class UserController{

    @GetMapping("/test1")
    public String test1(){
        return "test1 passed";
    }

    @GetMapping("/test2")
    public String test2(){
        return "passed test 2";
    }

    @GetMapping("/admin")
    public String admin(){
        return "passed admin";
    }
}