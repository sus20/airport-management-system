package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.entity.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightRepository extends MongoRepository<Flight, ObjectId> {
    Optional<Flight> findByFlightNumber(String flightNumber);
}