package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.entity.Flight;
import com.airport.passenger_checkin_service.domain.enums.FlightEventType;
import com.airport.passenger_checkin_service.mapper.FlightMapper;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightCreatedHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightEventType getSupportedEventType() {
        return FlightEventType.CREATED;
    }

    @Override
    public void handle(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(
                        flight -> log.info("Flight already exists with flight number: {}", payload.getFlightNumber()),
                        () -> {
                            Flight newFlight = flightMapper.toEntity(payload);
                            flightRepository.save(newFlight);
                            log.info("Flight created: {}", payload.getFlightNumber());
                        }
                );
    }
}