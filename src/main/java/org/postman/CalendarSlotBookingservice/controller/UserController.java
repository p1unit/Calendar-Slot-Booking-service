package org.postman.CalendarSlotBookingservice.controller;

import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.service.UserDetailsServiceImpl;
import org.postman.CalendarSlotBookingservice.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@RequestMapping("/")
@RestController
public class UserController{

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    SecurityServiceImpl securityService;

    @GetMapping("/")
    public String home() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        System.out.println(username+" "+authorities.iterator().next().getAuthority());

        return ("<h1>Welcome</h1>");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public String user() {
//        return "hi";
        return ("<h1>Welcome User</h1>");
    }

    @RequestMapping(path = "/register" , method = RequestMethod.POST
            ,produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResult.toString();
        }

        userRepository.save(user);
//        userDetailsService.autoLogin(user.getUserName(), user.getConfirmPassword());

        return "done";
    }
}