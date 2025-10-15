package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.enums.FlightEventType;
import com.airport.passenger_checkin_service.mapper.FlightMapper;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightUpdatedHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightEventType getSupportedEventType() {
        return FlightEventType.UPDATED;
    }

    @Override
    public void handle(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(existingFlight -> {
                    flightMapper.updateEntityFromPayload(payload, existingFlight);
                    flightRepository.save(existingFlight);
                    log.info("Flight updated: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for update: {}", payload.getFlightNumber()));
    }
}