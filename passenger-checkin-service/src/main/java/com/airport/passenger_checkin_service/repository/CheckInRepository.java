package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.CheckIn;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckInRepository extends MongoRepository<CheckIn, ObjectId> {
    boolean existsByFlightIdAndPassengerId(String flightId, String passengerId);
    boolean existsByFlightIdAndSeatNumbersContains(String flightId, String seatNumber);
}