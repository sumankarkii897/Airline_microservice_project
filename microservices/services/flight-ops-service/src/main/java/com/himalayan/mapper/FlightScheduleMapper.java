package com.himalayan.mapper;

import com.himalayan.model.Flight;
import com.himalayan.model.FlightSchedule;
import com.himalayan.payload.request.FlightScheduleRequest;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.FlightScheduleResponse;
import org.springframework.stereotype.Component;

@Component
public class FlightScheduleMapper {

    public FlightSchedule toEntity
            (FlightScheduleRequest request,
             Flight flight) {
        if(flight == null) {
            throw new IllegalArgumentException("Flight is required");
        }
        if(request == null) {
            throw new IllegalArgumentException("Request is required");
        }

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive()!=null?request.getIsActive():true)


                .build();
    }

    public FlightScheduleResponse toResponse(FlightSchedule entity,
                                             AirportResponse departure,
                                             AirportResponse arrival) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity is required");
        }
        if(departure == null) {
            throw new IllegalArgumentException("Departure is required");
        }
        if(arrival == null) {
            throw new IllegalArgumentException("Arrival is required");
        }
        return FlightScheduleResponse.builder()
                .id(entity.getId())
                .flightId(entity.getFlight()!=null?entity.getFlight().getId():null)
                .flightNumber(entity.getFlight()!=null?entity.getFlight().getFlightNumber():null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .operatingDays(entity.getOperatingDays())
                .isActive(entity.getIsActive())
                .build();
    }

    public void updateEntity(FlightScheduleRequest request,
                             FlightSchedule existing){
        if(request == null) {
            throw new IllegalArgumentException("Request is required");
        }
        if(existing == null) {
            throw new IllegalArgumentException("Entity is required");
        }
        if(request.getDepartureTime()!=null) {
            existing.setDepartureTime(request.getDepartureTime());
        }
        if(request.getArrivalTime()!=null) {
            existing.setArrivalTime(request.getArrivalTime());
        }
        if(request.getStartDate()!=null) {
            existing.setStartDate(request.getStartDate());
        }
        if(request.getEndDate()!=null) {
            existing.setEndDate(request.getEndDate());
        }
        if(request.getOperatingDays()!=null) {
            existing.setOperatingDays(request.getOperatingDays());
        }
        if(request.getIsActive()!=null) {
            existing.setIsActive(request.getIsActive());
        }
    }
}
