package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.dto.PassengerResponse;
import com.airport.passenger_checkin_service.dto.PassengerSearchRequest;
import org.bson.types.ObjectId;

import java.util.List;

public interface PassengerService {
    PassengerResponse createPassenger(PassengerRequest request);
    PassengerResponse getPassengerById(ObjectId id);
    List<PassengerResponse> getAllPassengers();
    PassengerResponse updatePassenger(ObjectId id, PassengerRequest request);
    void deletePassenger(ObjectId id);
    List<PassengerResponse> search(PassengerSearchRequest request);
}