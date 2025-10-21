package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.domain.dto.request.FlightCheckInRequest;
import com.airport.passenger_checkin_service.domain.dto.request.UpdateSeatRequest;
import com.airport.passenger_checkin_service.domain.dto.response.FlightCheckInResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface FlightCheckInService {
    FlightCheckInResponse createCheckIn(FlightCheckInRequest request);
    FlightCheckInResponse getCheckInById(ObjectId checkInId);
    List<FlightCheckInResponse> getCheckInsByFlightNumber(String flightNumber);
    FlightCheckInResponse updateSeatNumbers(ObjectId checkInId, UpdateSeatRequest request);
    void cancelCheckIn(ObjectId checkInId);
}