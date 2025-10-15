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
public class TerminalUpdatedHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;

    @Override
    public FlightEventType getSupportedEventType() {
        return FlightEventType.TERMINAL_UPDATED;
    }

    @Override
    public void handle(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flight.setTerminal(payload.getTerminal());
                    flightRepository.save(flight);
                    log.info("Terminal updated for flight: {}", payload.getFlightNumber());
                }, () -> log.warn("Cannot update terminal — flight not found: {}", payload.getFlightNumber()));
    }
}