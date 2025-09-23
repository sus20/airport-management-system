package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.dto.CheckInRequest;
import com.airport.passenger_checkin_service.dto.CheckInResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface CheckInService {
    CheckInResponse checkIn(CheckInRequest request);
    CheckInResponse getCheckIn(ObjectId id);
    List<CheckInResponse> getCheckInsForFlight(ObjectId flightId);
    CheckInResponse updateSeat(ObjectId checkInId, String seatNumber);
    CheckInResponse updateBaggage(ObjectId checkInId, int baggageCount);
    void cancelCheckIn(ObjectId checkInId);
}