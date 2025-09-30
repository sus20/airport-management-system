package com.airport.flightscheduler.service.update.ops;

import com.airport.flightscheduler.domain.dto.request.FlightOpsRequest;
import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.service.update.FlightUpdateHandler;
import org.springframework.stereotype.Component;

@Component
public class StatusUpdateHandler implements FlightUpdateHandler<FlightOpsRequest> {
    @Override
    public Class<FlightOpsRequest> getSupportedType() {
        return FlightOpsRequest.class;
    }

    @Override
    public UpdateType getUpdateType() {
        return UpdateType.STATUS_UPDATED;
    }

    @Override
    public Flight applyUpdate(Flight flight, FlightOpsRequest request) {
        if (request.getFlightStatus() != null
                && !request.getFlightStatus().equals(flight.getStatus())) {
            flight.setStatus(request.getFlightStatus());
        }
        return flight;
    }
}