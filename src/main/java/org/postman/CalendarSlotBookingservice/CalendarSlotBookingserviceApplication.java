package org.postman.CalendarSlotBookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class CalendarSlotBookingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarSlotBookingserviceApplication.class, args);
	}

}
