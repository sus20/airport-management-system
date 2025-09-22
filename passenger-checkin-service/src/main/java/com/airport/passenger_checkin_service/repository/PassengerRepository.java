package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.Passenger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PassengerRepository extends MongoRepository<Passenger, ObjectId> {
    Optional<Passenger> findByPassportNumberAndPassportCountry(String passportNumber, String passportCountry);
}