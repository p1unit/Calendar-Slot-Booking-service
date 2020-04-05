package org.postman.CalendarSlotBookingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "appointment")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private long id;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private Time appointmentStartTime;

    @Column(nullable = false)
    private Time appointmentEndTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "user_id" ,nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User creator;

    private String bookedBy;

    @Email
    private String bookerEmail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(Time appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public Time getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(Time appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }
}