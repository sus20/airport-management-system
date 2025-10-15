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
public class FlightDeletedHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;


    @Override
    public FlightEventType getSupportedEventType() {
        return FlightEventType.DELETED;
    }

    @Override
    public void handle(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flightRepository.delete(flight);
                    log.info("Flight deleted: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for deletion: {}", payload.getFlightNumber()));
    }
}