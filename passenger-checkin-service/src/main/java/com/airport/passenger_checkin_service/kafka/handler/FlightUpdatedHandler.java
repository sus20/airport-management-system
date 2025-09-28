package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlightUpdatedHandler implements FlightEventHandler{
    private final FlightRepository flightRepository;

    @Override
    public void handle(FlightEvent event) {
        flightRepository.save(event.getFlight());
    }

    @Override
    public String getType() {
        return "UPDATE";
    }
}