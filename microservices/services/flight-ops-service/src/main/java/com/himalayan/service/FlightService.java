package com.himalayan.service;

import com.himalayan.model.Flight;
import com.himalayan.payload.request.FlightRequest;
import com.himalayan.payload.request.UpdateFlightStatus;
import com.himalayan.payload.response.FlightResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    FlightResponse createFlight(FlightRequest flightRequest, Long airlineId);
    PageResponse<FlightResponse> getFlights(Pageable pageable);
    PageResponse<FlightResponse> getFlightsByAirline(Long airlineId,
     Long departureAirportId, Long arrivalAirportId,
            Pageable pageable);
    FlightResponse getFlightById(Long id);
    FlightResponse updateFlight(FlightRequest flightRequest, Long id);
    void deleteFlight(Long id);
    FlightResponse changeStatus(UpdateFlightStatus flightStatus, Long id);

}
