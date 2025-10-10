package com.airport.flightscheduler.service;

import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.domain.dto.response.FlightResponse;
import com.airport.flightscheduler.domain.enums.FlightStatus;
import org.bson.types.ObjectId;

import java.util.List;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);

    List<FlightResponse> getAllFlights(int page, int size);

    FlightResponse getFlightById(ObjectId id);

    FlightResponse updateFlight(ObjectId id, FlightRequest request);

    FlightResponse updateGate(ObjectId id, String gate);

    FlightResponse updateTerminal(ObjectId id, String terminal);

    FlightResponse updateStatus(ObjectId id, FlightStatus status);

    void deleteFlight(ObjectId id);

    List<FlightResponse> search(FlightSearchRequest request);
}