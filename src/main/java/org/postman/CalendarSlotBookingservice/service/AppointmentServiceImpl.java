package org.postman.CalendarSlotBookingservice.service;

import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;


    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findAllByAppointmentDateBetween(startDate, endDate);
    }

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long appointmentId, Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateStatus(Long appointmentId, Appointment appointment) {

        // ToDo - manage it

//        Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);
//
//        if(appointmentList.isPresent()){
//            if(appointment.getAppointmentStatus() != null){
//                appointmentList.get().setAppointmentStatus(appointment.getAppointmentStatus());
//            }
//            return appointmentRepository.save(appointmentList.get());
//        }
        return null;
    }

    @Override
    public void deleteById(Long appointmentId) {
        // ToDo - manage it
    }
}