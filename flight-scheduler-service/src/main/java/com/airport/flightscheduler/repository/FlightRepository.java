package com.airport.flightscheduler.repository;

import com.airport.flightscheduler.domain.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<Flight, ObjectId> {
}
