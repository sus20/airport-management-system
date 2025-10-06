package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.entity.FlightDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDetails toEntity(FlightPayload payload);
    FlightPayload toDto(FlightDetails entity);
}