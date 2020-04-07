package org.postman.CalendarSlotBookingservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchAppointment {

    @ApiModelProperty(notes = "appointment Date")
    private LocalDate appointmentDate;

    private ArrayList<AppointmentSlot> appointmentSlots;

}