package com.airport.flightscheduler.specification.criteria;

import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface FlightSearchCriterion {
    Optional<Criteria> toCriteria(FlightSearchRequest request);
}