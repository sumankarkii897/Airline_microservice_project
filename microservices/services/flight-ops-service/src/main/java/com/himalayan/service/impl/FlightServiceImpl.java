package com.himalayan.service.impl;

import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.InvalidRequestException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.FlightMapper;
import com.himalayan.model.Flight;
import com.himalayan.payload.request.FlightRequest;
import com.himalayan.payload.request.UpdateFlightStatus;
import com.himalayan.payload.response.*;
import com.himalayan.repository.FlightRepository;
import com.himalayan.service.FlightService;
import com.himalayan.util.PageMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final PageMapper pageMapper;
    private final FlightMapper flightMapper;
    @Override
    public FlightResponse createFlight(FlightRequest flightRequest, Long airlineId) {
        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new AlreadyExistsException("flight number already exists");
        }

        flightRequest.setAirlineId(airlineId);
        Flight flight = flightMapper.toEntity(flightRequest);
        Flight savedFlight = flightRepository.save(flight);

        return convertToFlightResponse(savedFlight);
    }

    @Override
    public PageResponse<FlightResponse> getFlights(Pageable pageable) {
        Page<Flight> flights=flightRepository.findAll(pageable);
        return pageMapper.toPageResponse(flights.map(this::convertToFlightResponse));
    }

    @Override
    public PageResponse<FlightResponse> getFlightsByAirline(Long airlineId,
                                                            Long departureAirportId,
                                                            Long arrivalAirportId,
                                                            Pageable pageable) {
        Page<Flight> flights = flightRepository.findByAirlineId(
                    airlineId,departureAirportId,arrivalAirportId,pageable
        );
        return pageMapper.toPageResponse(
                flights.map(this::convertToFlightResponse)
        );
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight not found with id : "+ id)
        );

        return convertToFlightResponse(flight);
    }

    @Override
    @Transactional
    public FlightResponse updateFlight(FlightRequest flightRequest,
                                       Long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight not found with id : "+ id)
        );
        if(flightRequest.getFlightNumber()!=null &&
                !flight.getFlightNumber().equals(flightRequest.getFlightNumber()) &&
                flightRepository.existsByFlightNumberAndAirlineId(
                        flightRequest.getFlightNumber(),
                        flight.getAirlineId()
                )        ){
            throw new AlreadyExistsException("flight number already exists");
        }
        flightMapper.updateFlight(flight,flightRequest);
        return convertToFlightResponse(flight);
    }

    @Override
    @Transactional
    public void deleteFlight(Long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight not found with id : "+ id)
        );
        flightRepository.delete(flight);

    }

    @Override
    @Transactional
    public FlightResponse changeStatus(UpdateFlightStatus flightStatus, Long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight not found with id : "+ id)
        );

        if(flightStatus.getStatus() == null){
            throw new InvalidRequestException("Flight status is required");
        }
        flightMapper.updateFlightStatus(flight, flightStatus);

        return convertToFlightResponse(flight);    }

    public FlightResponse convertToFlightResponse(Flight flight) {
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();
        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return flightMapper.toResponse(
                flight,
                departureAirport,
                arrivalAirport,
                airline,
                aircraft
        );
    }
}

