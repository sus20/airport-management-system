package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.enums.FlightEventType;

public interface FlightEventHandler {
    FlightEventType getSupportedEventType();
    void handle(FlightPayload payload);
}