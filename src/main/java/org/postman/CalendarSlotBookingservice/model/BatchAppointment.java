package org.postman.CalendarSlotBookingservice.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class BatchAppointment {

    private LocalDate appointmentDate;

    private ArrayList<AppointmentSlot> appointmentSlots;

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public ArrayList<AppointmentSlot> getAppointmentSlots() {
        return appointmentSlots;
    }

    public void setAppointmentSlots(ArrayList<AppointmentSlot> appointmentSlots) {
        this.appointmentSlots = appointmentSlots;
    }
}