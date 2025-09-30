package com.airport.flightscheduler.domain.dto.request.validation;

import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FlightRequestValidator implements ConstraintValidator<ValidFlightRequest, FlightRequest> {
    @Override
    public boolean isValid(FlightRequest flightRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        // Origin ≠ Destination
        if (flightRequest.getOrigin() != null && flightRequest.getDestination() != null &&
                flightRequest.getOrigin().equalsIgnoreCase(flightRequest.getDestination())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Origin and Destination must not be same")
                    .addConstraintViolation();
            valid = false;
        }

        //  Departure time ≠ Arrival time
        if (flightRequest.getDepartureTime() != null && flightRequest.getArrivalTime() != null
                && flightRequest.getDepartureTime().isEqual(flightRequest.getArrivalTime())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Departure and arrival times must not be the same")
                    .addConstraintViolation();
            valid = false;

        }

        // Departure time must be before Arrival time
        if (flightRequest.getDepartureTime() != null && flightRequest.getArrivalTime() != null &&
                flightRequest.getDepartureTime().isAfter(flightRequest.getArrivalTime())) {

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Departure time must be before arrival time")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }

}