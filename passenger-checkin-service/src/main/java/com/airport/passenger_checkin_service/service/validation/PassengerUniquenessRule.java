package com.airport.passenger_checkin_service.service.validation;

import com.airport.passenger_checkin_service.dto.PassengerRequest;

public interface PassengerUniquenessRule {
    void validate(PassengerRequest request);
}