package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.entity.FlightReference;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightRepository extends MongoRepository<FlightReference, ObjectId> {
    Optional<FlightReference> findByFlightNumber(String flightNumber);
}