package com.airport.flightscheduler.service.update;

import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;

public interface FlightUpdateHandler<T>{
Class<T> getSupportedType();
UpdateType getUpdateType();
void applyUpdate(Flight flight, T request);
}