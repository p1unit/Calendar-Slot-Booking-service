package org.postman.CalendarSlotBookingservice.controller;

import com.sun.istack.Nullable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.Response;
import org.postman.CalendarSlotBookingservice.exceptions.CustomMessage;
import org.postman.CalendarSlotBookingservice.exceptions.ResourceNotFoundException;
import org.postman.CalendarSlotBookingservice.model.Appointment;
import org.postman.CalendarSlotBookingservice.model.BatchAppointment;
import org.postman.CalendarSlotBookingservice.resource.EndPoints;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.postman.CalendarSlotBookingservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(EndPoints.API_VERSION+EndPoints.APPOINTMENT_BASE)
@Api(value = "Appointment End Points"   )
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @ApiOperation(value = "return all appointments")
    @RequestMapping(path = EndPoints.APPOINTMENT_ALL, method = RequestMethod.GET)
    ResponseEntity findAll() {
        return appointmentService.findAll();
    }

    @ApiOperation(value = "create new appointment")
    @PostMapping(path = EndPoints.APPOINTMENT_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody Appointment appointment) throws ResourceNotFoundException {
        return appointmentService.create(appointment);
    }

    @ApiOperation(value = "create batch appointment slot in a particular day")
    @PostMapping(path = EndPoints.BATCH_APPOINTMENT_CREATE)
    public ResponseEntity createBatch(@Valid @RequestBody BatchAppointment appointments) throws ResourceNotFoundException {
        return appointmentService.createBatchAppointment(appointments);
    }

    @ApiOperation(value = "return appointment by Id")
    @GetMapping(path = EndPoints.APPOINTMENT_BY_ID)
    public ResponseEntity findById(@PathVariable Long appointmentId) {
        return appointmentService.findById(appointmentId);
    }

    @ApiOperation(value = "updates the appointment")
    @PutMapping(path = EndPoints.APPOINTMENT_UPDATE)
    public ResponseEntity update(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.update(appointmentId, appointment);
    }

    @ApiOperation(value = "books the appointment using its Id")
    @PatchMapping(path = EndPoints.APPOINTMENT_BOOK)
    public ResponseEntity bookAppointment(@PathVariable Long appointmentId) {
        return appointmentService.bookAppointment(appointmentId);
    }

    @ApiOperation(value = "Cancel the appointment using its Id")
    @PatchMapping(path = EndPoints.APPOINTMENT_CANCEL)
    public ResponseEntity cancelAppointment(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    @ApiOperation(value = "Delete the appointment using its Id")
    @DeleteMapping( path = EndPoints.APPOINTMENT_DELETE)
    public ResponseEntity deleteById(@PathVariable Long appointmentId) {

        return appointmentService.deleteById(appointmentId);
    }

    @ApiOperation(value = "return the appointments between two dates")
    @GetMapping(path = EndPoints.APPOINTMENT_BETWEEN_DATES)
    public ResponseEntity findByDateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                                          @Nullable @RequestParam("status") String status) {

        return appointmentService.findByDateRangeWithStatus(startDate, endDate,status);
    }

    @ApiOperation(value = "return the appointments after a date")
    @GetMapping(path = EndPoints.APPOINTMENT_AFTER_DATES)
    public ResponseEntity findByDateAfter(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                          @Nullable @RequestParam("status") String status) {

        return appointmentService.findAllByAppointmentDateAfter(date,status);
    }

    @ApiOperation(value = "return the appointments before a date")
    @GetMapping(path = EndPoints.APPOINTMENT_BEFORE_DATES)
    public ResponseEntity findAllByDateBefore(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                                        @Nullable @RequestParam("status") String status) {

        return appointmentService.findAllByAppointmentDateBefore(date,status);
    }

    @ApiOperation(value = "return the appointments between two dates by a user using userId")
    @GetMapping(path = EndPoints.APPOINTMENT_BY_USER_BETWEEN_DATES)
    public ResponseEntity findByUserAndDateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                                          @Nullable @RequestParam("status") String status,@PathVariable("userId") Long userId) {

        return appointmentService.findByUserAndDateRangeWithStatus(startDate, endDate,userId,status);
    }

    @ApiOperation(value = "return the appointments after a date by a user using userId")
    @GetMapping(path = EndPoints.APPOINTMENT_BY_AFTER_DATES)
    public ResponseEntity findByUserAndDateAfter(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                          @Nullable @RequestParam("status") String status, @PathVariable("userId") Long userId) {

        return appointmentService.findAllByUserAndAppointmentDateAfter(date,userId,status);
    }

    @ApiOperation(value = "return the appointments before a date by a user using userId")
    @GetMapping(path = EndPoints.APPOINTMENT_BY_USER_BEFORE_DATES)
    public ResponseEntity findAllByUserAndDateBefore(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date,
                                              @Nullable @RequestParam("status") String status, @PathVariable("userId") Long userId) {

        return appointmentService.findAllByUserAndAppointmentDateBefore(date,userId,status);
    }
}