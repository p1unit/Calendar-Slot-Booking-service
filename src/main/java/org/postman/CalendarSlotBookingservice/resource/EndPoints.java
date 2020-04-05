package org.postman.CalendarSlotBookingservice.resource;

public class EndPoints {

    public static final String API_VERSION = "api/v1";

    // EndPoints user
    public static final String USER_BASE = "/user";
    public static final String USER_HOME = "/";
    public static final String CURRENT_USER = "/currentUser";
    public static final String REGISTER_USER = "/register";
    public static final String FIND_USER = "/{userId}";
    public static final String LOG_OUT = "/logout";

    // EndPoints appointment
    public static final String APPOINTMENT_BASE = "/appointment";
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
    public static final String APPOINTMENT_BY_USER_BETWEEN_DATES = "/all/betweenDate/{userId}";
    public static final String APPOINTMENT_BY_USER_BEFORE_DATES = "/all/beforeDate/{userId}";
    public static final String APPOINTMENT_BY_AFTER_DATES = "/all/afterDate/{userId}";
}