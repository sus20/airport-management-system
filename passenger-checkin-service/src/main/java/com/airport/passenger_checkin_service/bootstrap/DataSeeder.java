package com.airport.passenger_checkin_service.bootstrap;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerRequest;
import com.airport.passenger_checkin_service.exception.DuplicatePassengerException;
import com.airport.passenger_checkin_service.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"dev"})
public class DataSeeder implements CommandLineRunner {
    private final PassengerService passengerService;
    private final ObjectMapper objectMapper;
    private final Validator validator;


    @Override
    public void run(String... args) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passengers.json")) {

            if (inputStream == null) {
                log.warn("No Passengers.json file found in resources, skipping seeding.");
                return;
            }

            List<PassengerRequest> passengerRequestList = Arrays.asList(objectMapper.readValue(inputStream, PassengerRequest[].class));
            log.info("Seeding {} passengers from JSON.", passengerRequestList.size());

            passengerRequestList.forEach(passengerRequest -> {
                Set<ConstraintViolation<PassengerRequest>> violations = validator.validate(passengerRequest);
                if (!violations.isEmpty()) {
                    String violationMessages = violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(", "));
                    log.warn("Validation failed for Passenger {}: {}", passengerRequest.getPassportNumber(), violationMessages);
                    return;
                }
                try {
                    passengerService.createPassenger(passengerRequest);
                } catch (DuplicatePassengerException exception) {
                    log.debug("Passenger: {} already exists,skipping", passengerRequest.getPassportNumber());
                }
            });

        } catch (Exception exception) {
            log.error("Failed to seed initial flight data", exception);

        }

    }
}