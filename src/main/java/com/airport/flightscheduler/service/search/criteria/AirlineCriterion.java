package com.airport.flightscheduler.service.search.criteria;

import com.airport.flightscheduler.domain.FlightSearchRequest;
import com.airport.flightscheduler.service.search.FlightSearchCriterion;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class AirlineCriterion implements FlightSearchCriterion {

    @Override
    public Optional<Criteria> toCriteria(FlightSearchRequest request) {
        String airline = request.getAirline();
        if (airline == null || airline.isBlank()) return Optional.empty();
        return Optional.of(
                Criteria.where("airline")
                        .regex("^" + Pattern.quote(airline.trim()) + "$", "i")
        );
    }
}