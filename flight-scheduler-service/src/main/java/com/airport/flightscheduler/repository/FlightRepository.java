package com.airport.flightscheduler.repository;

import com.airport.flightscheduler.domain.entity.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends MongoRepository<Flight, ObjectId> {
    boolean existsByFlightNumberAndDepartureTime(String flightNumber, LocalDateTime departureTime);
}
