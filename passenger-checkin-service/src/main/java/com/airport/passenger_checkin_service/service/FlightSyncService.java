package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;

public interface FlightSyncService {
    void handleEvent(FlightEvent event);
}