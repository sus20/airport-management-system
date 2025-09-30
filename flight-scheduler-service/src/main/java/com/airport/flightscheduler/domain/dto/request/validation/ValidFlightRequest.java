package com.airport.flightscheduler.domain.dto.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlightRequestValidator.class)
@Documented
public @interface ValidFlightRequest {
    String message() default "Invalid Flight Request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}