package com.airport.flightscheduler.service.implementation;

import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.domain.dto.response.FlightResponse;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.exception.FlightAlreadyExistsException;
import com.airport.flightscheduler.exception.FlightNotFoundException;
import com.airport.flightscheduler.kafka.producer.FlightEventPublisher;
import com.airport.flightscheduler.mapper.FlightMapper;
import com.airport.flightscheduler.repository.FlightRepository;
import com.airport.flightscheduler.service.FlightService;
import com.airport.flightscheduler.service.update.FlightUpdateHandler;
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


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightSearchSpecification flightSearchSpecification;
    private final FlightMapper flightMapper;
    private final MongoTemplate mongoTemplate;
    private final FlightEventPublisher flightEventPublisher;
    private final List<FlightUpdateHandler<?>> handlers;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        validateFlightDoesNotExist(request);

        Flight flight = flightMapper.toEntity(request);
        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight saved: {}", savedFlight);

        flightEventPublisher.publishFlightCreated(savedFlight);

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
    public FlightResponse updateFlight(ObjectId id, Object request) {
        Flight flight = findFlightOrThrow(id);
        List<UpdateType> appliedUpdates = new ArrayList<>();

        for(FlightUpdateHandler<?> handler : handlers) {
            if(handler.getSupportedType().isInstance(request)) {
                applyTypedUpdate(handler,flight, request);
                appliedUpdates.add(handler.getUpdateType());
            }
        }
        Flight savedFlight = flightRepository.save(flight);

        for (UpdateType updateType : appliedUpdates) {
            flightEventPublisher.publishFlightUpdated(savedFlight, updateType);
        }

        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public void deleteFlight(ObjectId id) {
        Flight existing = findFlightOrThrow(id);
        flightRepository.deleteById(id);
        flightEventPublisher.publishFlightDeleted(existing);
    }

    @Override
    public List<FlightResponse> search(FlightSearchRequest request) {
        Query query = flightSearchSpecification.toQuery(request);
        List<Flight> result = mongoTemplate.find(query, Flight.class);
        return flightMapper.toResponseList(result);
    }

    private <T> void applyTypedUpdate(FlightUpdateHandler<T> handler, Flight flight, Object request) {
        handler.applyUpdate(flight, handler.getSupportedType().cast(request));
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
}