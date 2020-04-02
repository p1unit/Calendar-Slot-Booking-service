package org.postman.CalendarSlotBookingservice.service;

import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<User> user =  userRepository.findByUserName(userEmail);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userEmail));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.get().getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

//        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
//                user.get().getPassword(),grantedAuthorities);

        System.out.println(user.get().getUserName()+" "+userEmail);

        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),user.get().getPassword(), grantedAuthorities);
    }


}