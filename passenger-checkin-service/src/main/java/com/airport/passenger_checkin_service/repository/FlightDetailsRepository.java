package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.entity.FlightDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, ObjectId> {
    Optional<FlightDetails> findByFlightNumber(String flightNumber);
}