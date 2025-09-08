package com.airport.flightscheduler.mapper;

import com.airport.flightscheduler.domain.Flight;
import com.airport.flightscheduler.dto.FlightDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DTOMapper {
    FlightDTO toFlightDTO(Flight flight);
    List<FlightDTO> toFlightDTOs(List<Flight> flights);
}