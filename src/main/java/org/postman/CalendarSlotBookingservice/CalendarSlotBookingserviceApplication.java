package org.postman.CalendarSlotBookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Properties;

@SpringBootApplication
//@EnableJpaAuditing
public class CalendarSlotBookingserviceApplication {

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.put("server.port", 9999);

		new SpringApplicationBuilder(CalendarSlotBookingserviceApplication.class)
				.properties(props())
				.build()
				.run(args);
	}

	private static Properties props() {
		Properties properties = new Properties();
		properties.put("spring.datasource.url","jdbc:mysql://localhost:3306/springsecurity");
		properties.put("spring.datasource.username","punee");
		properties.put("spring.datasource.password","punee901@A");
		return properties;
	}

}
