package org.postman.CalendarSlotBookingservice.controller;

import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.SecurityService;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.service.SecurityServiceImpl;
import org.postman.CalendarSlotBookingservice.service.UserDetailsServiceImpl;
import org.postman.CalendarSlotBookingservice.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@RestController
public class UserController{

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    SecurityServiceImpl securityService;

//    @Autowired
//    SecurityServiceImpl securityService;

    @GetMapping("/")
    public CustomMessage home() {

        return new CustomMessage("Welcome in the Appointment booking system",HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public Optional<User> user() {
        return userRepository.findByUsername(securityService.findLoggedInUsername());
    }

    @PostMapping(path = "/register")

    public ResponseEntity<CustomMessage> register(@Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {

            List<String> errors = new ArrayList<>();
            bindingResult
                    .getAllErrors()
                    .forEach(f -> errors.add(f.getCode() + ": " + f.getDefaultMessage()));

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomMessage("Multiple Errors",HttpStatus.FORBIDDEN,errors));

        }

        User createdUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomMessage(StringResoures.USER_CREATED,HttpStatus.CREATED,createdUser));
    }
}