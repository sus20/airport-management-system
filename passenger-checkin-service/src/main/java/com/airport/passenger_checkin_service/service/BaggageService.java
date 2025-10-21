package com.airport.passenger_checkin_service.service;

import com.airport.passenger_checkin_service.domain.dto.request.BaggageRequest;
import com.airport.passenger_checkin_service.domain.dto.response.BaggageResponse;
import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import org.bson.types.ObjectId;

import java.util.List;

public interface BaggageService {
    List<ObjectId> createForCheckIn(ObjectId checkInId, List<BaggageRequest> baggages);
    BaggageResponse getBaggageById(ObjectId baggageId);
    List<BaggageResponse> getBaggageByCheckInId(ObjectId checkInId);
    BaggageResponse updateStatus(ObjectId baggageId, BaggageStatus status);
    BaggageResponse updateWeight(ObjectId baggageId, double weight);
}