package com.himalayan.mapper;

import com.himalayan.enums.FlightStatus;
import com.himalayan.model.Flight;
import com.himalayan.model.FlightInstance;
import com.himalayan.payload.request.FlightInstanceRequest;
import com.himalayan.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightInstanceMapper {

public FlightInstance toEntity(FlightInstanceRequest request, Flight flight)
{
    if(request == null || flight == null){
        throw new IllegalArgumentException("Request can't be null.");

    }
return FlightInstance.builder()
        .flight(flight)
        .airlineId(flight.getAirlineId())
        .scheduleId(request.getScheduleId())
        .departureAirportId(request.getDepartureAirportId()!=null?request.getDepartureAirportId():null)
        .arrivalAirportId(request.getArrivalAirportId()!=null?request.getArrivalAirportId():null)
        .departureDateTime(request.getDepartureDateTime())
        .arrivalDateTime(request.getArrivalDateTime())
        .totalSeats(request.getTotalSeats())
        .availableSeats(request.getAvailableSeats())
        .status(FlightStatus.SCHEDULED)
        .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
        .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
        .isActive(request.getIsActive()!=null?request.getIsActive():true)
        .build();
}

public FlightInstanceResponse toResponse(
        FlightInstance flightInstance,
        AircraftResponse aircraftResponse,
        AirlineResponse airline,
        AirportResponse departureAirport,
        AirportResponse arrivalAirport
){
    if(flightInstance == null){
        throw new IllegalArgumentException("flightInstance can't be null.");
    }
    return FlightInstanceResponse.builder()
            .id(flightInstance.getId())
            .flightId(flightInstance.getFlight()!=null?flightInstance.getFlight().getId():null)
            .flightNumber(flightInstance.getFlight()!=null ? flightInstance.getFlight().getFlightNumber() : null)
            .aircraftId(flightInstance.getFlight().getAircraftId())
            .aircraftModel(aircraftResponse.getModel())
            .aircraftCode(aircraftResponse.getCode())
            .airlineId(flightInstance.getAirlineId())
            .airlineName(airline.getName())
            .airlineLogo(airline.getLogoUrl())
            .departureAirport(departureAirport)
            .arrivalAirport(arrivalAirport)
            .formattedDuration(flightInstance.getFormattedDuration())
            .totalSeats(flightInstance.getTotalSeats())
            .availableSeats(flightInstance.getAvailableSeats())
            .status(flightInstance.getStatus())
            .minAdvanceBookingDays(flightInstance.getMinAdvanceBookingDays())
            .maxAdvanceBookingDays(flightInstance.getMaxAdvanceBookingDays())
            .isActive(flightInstance.getIsActive())
            .build();
}

public void updateFlightInstance(
        FlightInstanceRequest request,
        FlightInstance existing)
{
    if(request == null || existing == null){
        throw new IllegalArgumentException("Request can't be null.");
    }
    if(request.getDepartureAirportId()!=null){
        existing.setDepartureAirportId(request.getDepartureAirportId());
    }
    if(request.getArrivalAirportId()!=null){
        existing.setArrivalAirportId(request.getArrivalAirportId());
    }
    if(request.getDepartureDateTime()!=null){
        existing.setDepartureDateTime(request.getDepartureDateTime());
    }
    if(request.getArrivalDateTime()!=null){
        existing.setArrivalDateTime(request.getArrivalDateTime());
    }
//    if(request.getTotalSeats()!=null){
//        existing.setTotalSeats(request.getTotalSeats());
//    }
    if(request.getAvailableSeats()!=null){
        existing.setAvailableSeats(request.getAvailableSeats());
    }
    if(request.getIsActive()!=null){
        existing.setIsActive(request.getIsActive());
    }
    if(request.getStatus()!=null){
        existing.setStatus(request.getStatus());
    }
    if(request.getMinAdvanceBookingDays()!=null){
        existing.setMinAdvanceBookingDays(request.getMinAdvanceBookingDays());
    }
    if(request.getMaxAdvanceBookingDays()!=null){
        existing.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingDays());
    }
}
}
