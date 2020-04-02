package org.postman.CalendarSlotBookingservice.service;

import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.SecurityService;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service

public class UserDetailsServiceImpl implements UserDetailsService, SecurityService {

    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<User> user =  userRepository.findByUsername(userEmail);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userEmail));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.get().getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

//        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
//                user.get().getPassword(),grantedAuthorities);

        System.out.println(user.get().getUsername()+" "+userEmail);

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(), grantedAuthorities);
    }


    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

//    @Override
//    public void autoLogin(String username, String password) {
//        UserDetails userDetails = loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            logger.debug(String.format("Auto login %s successfully!", username));
//        }
//    }
}