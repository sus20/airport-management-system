package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.mapper.FlightMapper;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FlightCoreUpdatedHandler implements FlightEventHandler{
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public String getEventType() {
        return "UPDATED";
    }

    @Override
    public UpdateType getUpdateType() {
        return UpdateType.CORE_UPDATED;
    }

    @Override
    public void handle(FlightEvent event) {
        flightRepository.save(flightMapper.toEntity(event.getFlightPayload()));
        log.info("Core flight details updated for {}", event.getFlightPayload().getId());
    }
}