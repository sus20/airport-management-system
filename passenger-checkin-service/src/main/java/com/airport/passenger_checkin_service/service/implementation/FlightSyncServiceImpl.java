package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.entity.Flight;
import com.airport.passenger_checkin_service.domain.enums.FlightEventType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.mapper.FlightMapper;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import com.airport.passenger_checkin_service.service.FlightSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSyncServiceImpl implements FlightSyncService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public void handleEvent(FlightEvent event) {
        FlightPayload payload = event.getFlightPayload();
        FlightEventType eventType = event.getEventType();

        switch (eventType) {
            case CREATED -> handleCreate(payload);
            case UPDATED -> handleUpdate(payload);
            case DELETED -> handleDelete(payload);
            case GATE_UPDATED -> handleGateUpdate(payload);
            case TERMINAL_UPDATED -> handleTerminalUpdate(payload);
            case STATUS_UPDATED -> handleStatusUpdate(payload);
            default -> log.warn("Unhandled event type: {}", eventType);
        }
    }

    private void handleCreate(FlightPayload payload) {
        if (flightRepository.findByFlightNumber(payload.getFlightNumber()).isPresent()) {
            log.info("Flight already exists with flight number {}", payload.getFlightNumber());
            return;
        }

        Flight newFlight = flightMapper.toEntity(payload);
        flightRepository.save(newFlight);
        log.info("Flight {} created", payload.getFlightNumber());
    }

    private void handleUpdate(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(existingFlight -> {
                    flightMapper.updateEntityFromPayload(payload, existingFlight);
                    flightRepository.save(existingFlight);
                    log.info("Flight updated: {}", payload.getFlightNumber());

                }, () -> log.warn("Flight not found for update:{}", payload.getFlightNumber()));
    }

    private void handleDelete(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flightRepository.delete(flight);
                    log.info("Flight deleted: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for deletion: {}", payload.getFlightNumber()));
    }

    private void handleGateUpdate(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flight.setGate(payload.getGate());
                    flightRepository.save(flight);
                    log.info("Gate Updated for flight: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for gate update: {}", payload.getFlightNumber()));
    }

    private void handleTerminalUpdate(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flight.setTerminal(payload.getTerminal());
                    flightRepository.save(flight);
                    log.info("Terminal Updated for flight: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for terminal update: {}", payload.getFlightNumber()));

    }

    private void handleStatusUpdate(FlightPayload payload) {
        flightRepository.findByFlightNumber(payload.getFlightNumber())
                .ifPresentOrElse(flight -> {
                    flight.setStatus(payload.getStatus());
                    flightRepository.save(flight);
                    log.info("Status Updated for flight: {}", payload.getFlightNumber());
                }, () -> log.warn("Flight not found for Status update: {}", payload.getFlightNumber()));
    }
}
