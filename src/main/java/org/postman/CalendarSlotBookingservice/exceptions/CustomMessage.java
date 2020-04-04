package org.postman.CalendarSlotBookingservice.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomMessage {

    private String message;
    private HttpStatus status;
    private Object responseObject;
    private List<String> errors;

    public CustomMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public CustomMessage(String message, HttpStatus status, List<String> errors) {
        this.message = message;
        this.status = status;
        this.errors = errors;
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}