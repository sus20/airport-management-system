package com.airport.flightscheduler.service.update.core;

import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.service.update.FlightUpdateHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CoreFlightUpdateHandler implements FlightUpdateHandler<FlightRequest> {

    @Override
    public Class<FlightRequest> getSupportedType() {
        return FlightRequest.class;
    }

    @Override
    public UpdateType getUpdateType() {
        return UpdateType.CORE_UPDATED;
    }

    @Override
    public void applyUpdate(Flight flight, FlightRequest request) {

        if (!Objects.equals(flight.getArrivalTime(), request.getArrivalTime())) {
            flight.setArrivalTime(request.getArrivalTime());
        }

        if (!Objects.equals(flight.getDepartureTime(), request.getDepartureTime())) {
            flight.setDepartureTime(request.getDepartureTime());
        }

        if (!Objects.equals(flight.getOrigin(), request.getOrigin())) {
            flight.setOrigin(request.getOrigin());
        }

        if (!Objects.equals(flight.getDestination(), request.getDestination())) {
            flight.setDestination(request.getDestination());
        }

    }
}