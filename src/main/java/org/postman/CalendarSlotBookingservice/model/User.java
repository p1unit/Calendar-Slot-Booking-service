package org.postman.CalendarSlotBookingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @ApiModelProperty(notes = "User unique Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @ApiModelProperty(notes = "User unique Email Id")
    @Email
    @Column(name = "username", nullable = false,unique = true)
    private String username;

    @ApiModelProperty(notes = "User full name")
    @Column(name = "name",nullable = false)
    private String name;

    @ApiModelProperty(notes = "User password")
    @Column(name = "password",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(notes = "User confirm password")
    @Transient()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

}