package com.airport.flightscheduler.service.implementation;

import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.domain.dto.response.FlightResponse;
import com.airport.flightscheduler.domain.enums.FlightEventType;
import com.airport.flightscheduler.domain.enums.FlightStatus;
import com.airport.flightscheduler.exception.FlightAlreadyExistsException;
import com.airport.flightscheduler.exception.FlightNotFoundException;
import com.airport.flightscheduler.kafka.producer.FlightEventPublisher;
import com.airport.flightscheduler.mapper.FlightMapper;
import com.airport.flightscheduler.repository.FlightRepository;
import com.airport.flightscheduler.service.FlightService;
import com.airport.flightscheduler.specification.FlightSearchSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightSearchSpecification flightSearchSpecification;
    private final FlightMapper flightMapper;
    private final MongoTemplate mongoTemplate;
    private final FlightEventPublisher flightEventPublisher;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        validateFlightDoesNotExist(request);
        Flight savedFlight = saveAndPublish(flightMapper.toEntity(request),FlightEventType.CREATED);
        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public List<FlightResponse> getAllFlights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Flight> flights = flightRepository.findAll(pageable);
        return flightMapper.toResponseList(flights.getContent());
    }

    @Override
    public FlightResponse getFlightById(ObjectId id) {
        return flightMapper.toResponse(findFlightOrThrow(id));
    }

    @Override
    public FlightResponse updateFlight(ObjectId id, FlightRequest request) {
        Flight existing = findFlightOrThrow(id);

        Flight updatedCandidate = flightMapper.toEntity(request);
        updatedCandidate.setId(existing.getId());

        if (existing.equals(updatedCandidate)) {
            return flightMapper.toResponse(existing);
        }

        flightMapper.updateEntityFromRequest(request, existing);

        Flight saved = saveAndPublish(existing, FlightEventType.UPDATED);
        return flightMapper.toResponse(saved);
    }

    @Override
    public FlightResponse updateGate(ObjectId id, String gate) {
        Flight flight = findFlightOrThrow(id);
        if(Objects.equals(flight.getGate(),gate)){
            log.info("Gate is unchanged for flight{}: {}", id, gate);
            return flightMapper.toResponse(flight);
        }
        flight.setGate(gate);
        Flight savedFlight = saveAndPublish(flight, FlightEventType.GATE_UPDATED);
        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public FlightResponse updateTerminal(ObjectId id, String terminal) {
        Flight flight = findFlightOrThrow(id);
        if (Objects.equals(flight.getTerminal(), terminal)) {
            log.info("Terminal is unchanged for flight{}: {}", id, terminal);
            return flightMapper.toResponse(flight);
        }
        flight.setTerminal(terminal);
        Flight savedFlight = saveAndPublish(flight, FlightEventType.TERMINAL_UPDATED);
        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public FlightResponse updateStatus(ObjectId id, FlightStatus status) {
        Flight flight = findFlightOrThrow(id);
        if (flight.getStatus() == status) {
            log.info("Status is unchanged for flight{}: {}", id, status);
            return flightMapper.toResponse(flight);
        }
        flight.setStatus(status);
        Flight savedFlight = saveAndPublish(flight, FlightEventType.STATUS_UPDATED);
        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public void deleteFlight(ObjectId id) {
        Flight existing = findFlightOrThrow(id);
        flightRepository.deleteById(id);
        flightEventPublisher.publish(flightMapper.toFlightPayload(existing),FlightEventType.DELETED);
    }

    @Override
    public List<FlightResponse> search(FlightSearchRequest request) {
        Query query = flightSearchSpecification.toQuery(request);
        List<Flight> result = mongoTemplate.find(query, Flight.class);
        return flightMapper.toResponseList(result);
    }

    private Flight findFlightOrThrow(ObjectId id) {
        return flightRepository.findById(id).orElseThrow(() -> new FlightNotFoundException(id));
    }

    private void validateFlightDoesNotExist(FlightRequest request) {
        boolean exists = flightRepository.existsByFlightNumberAndDepartureTime(
                request.getFlightNumber(),
                request.getDepartureTime()
        );

        if (exists) {
            throw new FlightAlreadyExistsException(
                    "Flight already exists with flightNumber " + request.getFlightNumber()
                            + " at " + request.getDepartureTime()
            );
        }
    }

    private Flight saveAndPublish(Flight flight, FlightEventType eventType) {
        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight saved: {}", savedFlight);
        flightEventPublisher.publish(flightMapper.toFlightPayload(savedFlight), eventType);
        return savedFlight;
    }
}