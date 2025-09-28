package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightCancelledHandler implements FlightEventHandler {
    private final FlightRepository flightRepository;

    @Override
    public void handle(FlightEvent event) {
        flightRepository.findById(event.getFlight().getId())
                .ifPresent(flight -> {
                    flight.setStatus(FlightStatus.CANCELLED);
                    flightRepository.save(flight);
                });
    }

    @Override
    public String getType() {
        return "CANCELLED";
    }
}