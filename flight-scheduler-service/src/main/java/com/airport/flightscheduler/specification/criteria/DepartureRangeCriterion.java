package com.airport.flightscheduler.specification.criteria;

import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DepartureRangeCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(FlightSearchRequest request) {
        return range("departureTime", request.getDepartureFrom(), request.getDepartureTo());
    }
}