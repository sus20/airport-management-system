package com.airport.passenger_checkin_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PassengerCheckInServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PassengerCheckInServiceApplication.class, args);
	}
}
