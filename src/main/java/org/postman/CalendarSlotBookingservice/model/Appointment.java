package org.postman.CalendarSlotBookingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Appointment implements Serializable {

    @ApiModelProperty(notes = "appointment Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private long id;

    @ApiModelProperty(notes = "timestamp of the creation of the appointment")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @ApiModelProperty(notes = "appointment Date")
    @Column(nullable = false)
    private LocalDate appointmentDate;

    @ApiModelProperty(notes = "appointment  slot start time")
    @Column(nullable = false)
    private Time appointmentStartTime;

    @ApiModelProperty(notes = "appointment  slot end time")
    @Column(nullable = false)
    private Time appointmentEndTime;

    @ApiModelProperty(notes = "appointment status booked / available")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ApiModelProperty(notes = "appointment  creator user info")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "user_id" ,nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User creator;

    @ApiModelProperty(notes = "appointment booker user info")
    private String bookedBy;

    @ApiModelProperty(notes = "appointment booker email info")
    @Email
    private String bookerEmail;

    }