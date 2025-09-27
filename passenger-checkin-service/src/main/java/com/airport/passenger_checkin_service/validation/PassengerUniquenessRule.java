package com.airport.passenger_checkin_service.validation;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerRequest;

public interface PassengerUniquenessRule {
    void validate(PassengerRequest request);
}