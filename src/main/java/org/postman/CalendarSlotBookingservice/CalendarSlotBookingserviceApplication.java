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

		System.out.println("Variable " + System.getenv("DATABASE_PATH"));

		Properties properties = new Properties();
		properties.put("server.port", 9999);

		new SpringApplicationBuilder(CalendarSlotBookingserviceApplication.class)
				.properties(props())
				.build()
				.run(args);
	}

	private static Properties props() {
		Properties properties = new Properties();
		properties.put("spring.datasource.url",System.getenv("DATABASE_PATH"));
		properties.put("spring.datasource.username",System.getenv("USER"));
		properties.put("spring.datasource.password",System.getenv("PASSWORD"));
		return properties;
	}

}
