package org.postman.CalendarSlotBookingservice.resource;

public class EndPoints {

    // EndPoints
    public static final String APPOINTMENT_BASE = "api/v1/appointment";
    public static final String APPOINTMENT_ALL = "/all";
    public static final String APPOINTMENT_CREATE = "/create";
    public static final String APPOINTMENT_BY_ID = "/{appointmentId}";
    public static final String APPOINTMENT_UPDATE = "/update/{appointmentId}";
    public static final String APPOINTMENT_BOOK = "/book/{appointmentId}";
    public static final String APPOINTMENT_CANCEL = "/cancel/{appointmentId}";
    public static final String APPOINTMENT_DELETE="/{appointmentId}";
    public static final String APPOINTMENT_BETWEEN_DATES = "/all/betweenDate";
    public static final String APPOINTMENT_BEFORE_DATES = "/all/beforeDate";
    public static final String APPOINTMENT_AFTER_DATES = "/all/afterDate";


}