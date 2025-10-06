package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.mapper.FlightMapper;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightCreatedHandler implements FlightEventHandler{
    private final FlightRepository repository;
    private final FlightMapper mapper;

    @Override
    public String getEventType() {
        return "CREATED";
    }

    @Override
    public UpdateType getUpdateType() {
        return null;
    }

    @Override
    public void handle(FlightEvent event) {
        if(event.getFlightPayload() == null) {
            log.warn("Received FlightEvent without payload: {}", event);
            return;
        }
        repository.save(mapper.toEntity(event.getFlightPayload()));
        log.info("Saved flight {} locally", event.getFlightPayload().getId());
    }
}