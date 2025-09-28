package com.airport.flightscheduler.mapper;

import com.airport.flightscheduler.domain.entity.Flight;

import com.airport.flightscheduler.domain.dto.request.FlightRequest;
import com.airport.flightscheduler.domain.dto.response.FlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toEntity(FlightRequest request);

    @Mapping(target = "id", expression = "java(flight.getId() != null ? flight.getId().toHexString() : null)")
    FlightResponse toResponse(Flight flight);

    @Mapping(target = "id", ignore = true)

    void updateEntityFromRequest(FlightRequest request, @MappingTarget Flight flight);

    List<FlightResponse> toResponseList(List<Flight> flights);
}