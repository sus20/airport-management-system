package com.airport.flightscheduler.specification.criteria;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
public abstract class BaseCriterion implements FlightSearchCriterion {

    protected Optional<Criteria> matchFieldWithCaseInsensitiveRegex (String field, String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        return Optional.of(Criteria.where(field).regex("^" + Pattern.compile(value.trim(),Pattern.CASE_INSENSITIVE)));
    }

    protected Optional<Criteria> matchFieldStrictUppercase(String field, String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        return Optional.of(Criteria.where(field).is(value.trim().toUpperCase()));
    }

    protected Optional<Criteria> range(String field, Comparable<?> from, Comparable<?> to) {
        if (from == null && to == null) return Optional.empty();
        Criteria criteria = Criteria.where(field);
        if (from != null) criteria = criteria.gte(from);
        if (to != null) criteria = criteria.lte(to);
        return Optional.of(criteria);
    }
}