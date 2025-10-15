package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.entity.Flight;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toEntity(FlightPayload payload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromPayload(FlightPayload payload, @MappingTarget Flight entity);

}