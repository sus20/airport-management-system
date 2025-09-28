package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;

public interface FlightEventHandler {
    void handle(FlightEvent event);
    String getType();
}