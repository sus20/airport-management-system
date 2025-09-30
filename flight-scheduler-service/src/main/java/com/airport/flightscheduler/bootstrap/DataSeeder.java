package com.airport.flightscheduler.bootstrap;

import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.exception.FlightAlreadyExistsException;
import com.airport.flightscheduler.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final FlightService flightService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Override
    public void run(String... args) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json")) {

            if (inputStream == null) {
                log.warn("No flights.json file found in resources, skipping seeding.");
                return;
            }

            List<FlightRequest> flightRequests = Arrays.asList(objectMapper.readValue(inputStream, FlightRequest[].class));

            log.info("Seeding {} flights from JSON", flightRequests.size());

            flightRequests.forEach(flightRequest -> {
                Set<ConstraintViolation<FlightRequest>> violations = validator.validate(flightRequest);
                if (!violations.isEmpty()) {
                    String violationMessages = violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(", "));
                    log.warn("Validation failed for flight {}: {}", flightRequest.getFlightNumber(), violationMessages);
                    return;
                }
                try {
                    flightService.createFlight(flightRequest);
                } catch (FlightAlreadyExistsException e) {
                    log.debug("Flight {} already exists, skipping", flightRequest.getFlightNumber());
                }
            });

        } catch (Exception e) {
            log.error("Failed to seed initial flight data", e);
        }

    }
}