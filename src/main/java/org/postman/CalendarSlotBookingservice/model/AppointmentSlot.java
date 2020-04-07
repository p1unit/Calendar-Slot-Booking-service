package org.postman.CalendarSlotBookingservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlot {

    @ApiModelProperty(notes = "appointment slot start time")
    private Time appointmentStartTime;

    @ApiModelProperty(notes = "appointment slot end time")
    private Time appointmentEndTime;

    public Time getAppointmentStartTime() {
        return appointmentStartTime;
    }

}