package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CheckInRepository extends MongoRepository<FlightCheckInRecord, ObjectId> {
    List<FlightCheckInRecord> findByFlightNumber(String flightNumber);
    boolean existsByFlightNumberAndPassportNumber(String flightNumber, String passportNumber);
    boolean existsByFlightNumberAndSeatNumbersContains(String flightNumber, String seatNumber);
}