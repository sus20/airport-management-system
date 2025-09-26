package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.FlightCheckInRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CheckInRepository extends MongoRepository<FlightCheckInRecord, ObjectId> {
    List<FlightCheckInRecord> findByFlightId(ObjectId flightId);
    boolean existsByFlightIdAndPassportNumber(ObjectId flightId, String passportNumber);
    boolean existsByFlightIdAndSeatNumbersContains(ObjectId flightId, String seatNumber);
}