package com.airport.flightscheduler.service.update.ops;

import com.airport.flightscheduler.domain.dto.request.FlightOpsRequest;
import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.service.update.FlightUpdateHandler;
import org.springframework.stereotype.Component;

@Component
public class GateUpdateHandler implements FlightUpdateHandler<FlightOpsRequest> {

    @Override
    public Class<FlightOpsRequest> getSupportedType() {
        return FlightOpsRequest.class;
    }

    @Override
    public UpdateType getUpdateType() {
        return UpdateType.GATE_UPDATED;
    }

    @Override
    public Flight applyUpdate(Flight flight, FlightOpsRequest request) {
        if (request.getGate() != null && !request.getGate().equals(flight.getGate())) {
            flight.setGate(request.getGate());
        }
        return flight;
    }
}