package com.airport.passenger_checkin_service.search.criteria.passenger;

import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.search.BaseCriterion;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public class FirstNameCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(PassengerRequest request) {
        return matchFieldWithCaseInsensitiveRegex("firstName", request.getFirstName());
    }
}