package com.airport.flightscheduler.service.update.ops;

import com.airport.flightscheduler.domain.dto.request.FlightOpsRequest;
import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.service.update.FlightUpdateHandler;
import org.springframework.stereotype.Component;

@Component
public class TerminalUpdateHandler implements FlightUpdateHandler<FlightOpsRequest> {
    @Override
    public Class<FlightOpsRequest> getSupportedType() {
        return FlightOpsRequest.class;
    }

    @Override
    public UpdateType getUpdateType() {
        return UpdateType.TERMINAL_UPDATED;
    }

    @Override
    public void applyUpdate(Flight flight, FlightOpsRequest request) {
        if (request.getTerminal() != null && !request.getTerminal().equals(flight.getTerminal())) {
            flight.setTerminal(request.getTerminal());
        }
    }
}