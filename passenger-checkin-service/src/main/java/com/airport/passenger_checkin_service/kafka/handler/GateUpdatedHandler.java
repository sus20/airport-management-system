package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.enums.FlightEventType;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GateUpdatedHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;

    @Override
    public FlightEventType getSupportedEventType() {
        return FlightEventType.GATE_UPDATED;
    }

    @Override
    public void handle(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flight.setGate(payload.getGate());
                    flightRepository.save(flight);
                    log.info("Gate updated for flight: {}", payload.getFlightNumber());
                }, () -> log.warn("Cannot update gate — flight not found: {}", payload.getFlightNumber()));
    }
}