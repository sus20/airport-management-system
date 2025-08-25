package com.airport.flightscheduler.search.criteria;

import com.airport.flightscheduler.dto.FlightSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GateCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(FlightSearchRequest request) {
        return matchFieldWithCaseInsensitiveRegex("gate", request.getGate());
    }
}