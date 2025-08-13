package com.airport.flightscheduler.service;

import com.airport.flightscheduler.domain.Flight;
import com.airport.flightscheduler.dto.FlightDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface FlightService {
    FlightDTO createFlight(Flight flight);

    List<FlightDTO> getAllFlights(int page, int size);

    FlightDTO getFlightById(ObjectId id);

    FlightDTO updateFlight(ObjectId id, Flight flight);

    void deleteFlight(ObjectId id);
}