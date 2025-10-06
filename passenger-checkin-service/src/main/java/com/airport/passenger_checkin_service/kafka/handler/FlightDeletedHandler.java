package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.repository.FlightDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightDeletedHandler implements FlightEventHandler {
    private final FlightDetailsRepository flightRepository;

    @Override
    public String getEventType() {
        return "DELETED";
    }

    @Override
    public UpdateType getUpdateType() {
        return null;
    }

    @Override
    public void handle(FlightEvent event) {
        String flightId = event.getFlightPayload().getId();
        flightRepository.deleteById(new ObjectId(flightId));
        log.info("Deleted flight {} locally", event.getFlightPayload().getId());

    }
}