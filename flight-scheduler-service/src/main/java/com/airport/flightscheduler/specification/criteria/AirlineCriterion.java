package com.airport.flightscheduler.specification.criteria;

import com.airport.flightscheduler.domain.dto.request.FlightSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AirlineCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(FlightSearchRequest request) {
        log.info("Airline: {}", request.getAirline());
        return matchFieldWithCaseInsensitiveRegex("airline", request.getAirline());
    }
}