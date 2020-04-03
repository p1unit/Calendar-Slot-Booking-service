package org.postman.CalendarSlotBookingservice.exceptions;

import org.springframework.http.HttpStatus;

public class CustomMessage {

    private String message;
    private HttpStatus status;
    private Object responseObject;

    public CustomMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public CustomMessage(String message, HttpStatus status, Object object) {
        this.message = message;
        this.status = status;
        this.responseObject = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}