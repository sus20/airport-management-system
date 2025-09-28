package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightCreatedHandler implements FlightEventHandler{
    private final FlightRepository flightRepository;


    @Override
    public void handle(FlightEvent event) {
        flightRepository.save(event.getFlightReference());
    }

    @Override
    public String getType() {
        return "CREATED";
    }
}