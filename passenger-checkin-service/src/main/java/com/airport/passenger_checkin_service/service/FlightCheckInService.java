package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.dto.FlightCheckInRequest;
import com.airport.passenger_checkin_service.dto.FlightCheckInResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface FlightCheckInService {
    FlightCheckInResponse createCheckIn(FlightCheckInRequest request);
    FlightCheckInResponse getCheckInById(ObjectId checkInId);
    List<FlightCheckInResponse> getCheckInsByFlightId(ObjectId flightId);
    FlightCheckInResponse updateSeatAssignment(ObjectId checkInId, String seatNumber);
    FlightCheckInResponse updateBaggageInfo(ObjectId checkInId, int baggageCount);
    void cancelCheckIn(ObjectId checkInId);
}