package com.himalayan.mapper;

import com.himalayan.model.Flight;
import com.himalayan.payload.request.FlightRequest;
import com.himalayan.payload.request.UpdateFlightStatus;
import com.himalayan.payload.response.AircraftResponse;
import com.himalayan.payload.response.AirlineResponse;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
public Flight toEntity(FlightRequest flightRequest){
    if(flightRequest == null){
        throw new IllegalArgumentException( "Flight request cannot be null");
    }
    return Flight.builder()
            .flightNumber(flightRequest.getFlightNumber())
            .airlineId(flightRequest.getAirlineId())
            .aircraftId(flightRequest.getAircraftId())
            .departureAirportId(flightRequest.getDepartureAirportId())
            .arrivalAirportId(flightRequest.getArrivalAirportId())
            .status(flightRequest.getStatus())

            .build();
}

public FlightResponse toResponse(
  Flight flight, AirportResponse departureAirport,
  AirportResponse arrivalAirport, AirlineResponse airlineResponse,
  AircraftResponse aircraft
){
    if(flight == null){
        throw new IllegalArgumentException("Flight cannot be null");
    }

    return  FlightResponse.builder()
           .id(flight.getId())
           .flightNumber(flight.getFlightNumber())
           .airline(airlineResponse)
           .aircraft(aircraft)
           .departureAirport(departureAirport)
           .arrivalAirport(arrivalAirport)
           .status(flight.getStatus())
           .createdAt(flight.getCreatedAt())
           .updatedAt(flight.getUpdatedAt())

           .build();

}

public void updateFlight(Flight flight, FlightRequest flightRequest){
    if(flight == null){
        throw new IllegalArgumentException("flight cannot be null");
    }
    if(flightRequest == null){
        throw new IllegalArgumentException("flightRequest cannot be null");
    }
    if(flightRequest.getFlightNumber() != null){
        flight.setFlightNumber(flightRequest.getFlightNumber());

    }
    if(flightRequest.getAirlineId() != null){
        flight.setAirlineId(flightRequest.getAirlineId());
    }
    if(flightRequest.getAircraftId() != null){
        flight.setAircraftId(flightRequest.getAircraftId());
    }
    if(flightRequest.getDepartureAirportId() != null){
        flight.setDepartureAirportId(flightRequest.getDepartureAirportId());
    }
    if(flightRequest.getArrivalAirportId() != null){
        flight.setArrivalAirportId(flightRequest.getArrivalAirportId());
    }
    if(flightRequest.getStatus() != null){
        flight.setStatus(flightRequest.getStatus());
    }

}

public void updateFlightStatus(Flight flight, UpdateFlightStatus flightRequest){
    if(flight == null){
        throw new IllegalArgumentException("flight cannot be null");
    }
    if(flightRequest == null){
        throw new IllegalArgumentException("flightRequest cannot be null");
    }
    if(flightRequest.getStatus()!= null){
        flight.setStatus(flightRequest.getStatus());
    }

}


}
