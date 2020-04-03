package org.postman.CalendarSlotBookingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AppointmentStatus {
    @JsonProperty("available")
    Available,
    @JsonProperty("booked")
    Booked;
}
