package com.airport.flightscheduler.search;

import com.airport.flightscheduler.dto.FlightSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightSearchSpecification {

    private final List<FlightSearchCriterion> criteria;

    public Query toQuery(FlightSearchRequest request) {
        List<Criteria> parts = criteria.stream()
                .map(criterion -> criterion.toCriteria(request))
                .flatMap(Optional::stream)
                .toList();

        log.info("Criteria parts: {}", criteria);

        Query query = new Query();
        if (!parts.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(parts.toArray(new Criteria[0])));
        }

        log.info("Criteria beans: {}", criteria);
        return query;
    }
}