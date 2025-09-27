package com.airport.passenger_checkin_service.specification.criteria.passenger;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LastNameCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(PassengerSearchRequest request) {
        return matchFieldWithCaseInsensitiveRegex("lastName", request.getLastName());
    }
}