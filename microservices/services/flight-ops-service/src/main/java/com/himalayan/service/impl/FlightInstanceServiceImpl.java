package com.himalayan.service.impl;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.FlightInstanceMapper;
import com.himalayan.model.Flight;
import com.himalayan.model.FlightInstance;
import com.himalayan.payload.request.FlightInstanceRequest;
import com.himalayan.payload.response.*;
import com.himalayan.repository.FlightInstanceRepository;
import com.himalayan.repository.FlightRepository;
import com.himalayan.service.FlightInstanceService;
import com.himalayan.util.PageMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {
    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;
    private final PageMapper pageMapper;
    private final FlightInstanceMapper flightInstanceMapper;
    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request)
    {
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                        ()-> new ResourceNotFoundException("Flight Not Found.")
                );

        // dummy aircraft
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();
        FlightInstance flightInstance =flightInstanceMapper.toEntity(request,flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved=flightInstanceRepository.save(flightInstance);
// todo : create seat instances


        return convertToFlightInstanceResponse(saved);
    }

    @Override
    @Transactional
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) {

        FlightInstance flightInstance = flightInstanceRepository.findById(request.getFlightId()).orElseThrow(
                ()-> new ResourceNotFoundException("Flight Not Found.")
        );
        flightInstanceMapper.updateFlightInstance(request,flightInstance);
        return convertToFlightInstanceResponse(
                flightInstanceRepository.save(flightInstance));
    }

    @Override
    public void deleteFlightInstance(Long id) {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight Instance Not Found.")
        );
        flightInstanceRepository.delete(flightInstance);

    }

    @Override
    public PageResponse<FlightInstanceResponse> getFlightInstance(Pageable pageable) {
        Page<FlightInstance> flightInstancePage = flightInstanceRepository.findAll(pageable);

        return pageMapper.toPageResponse(flightInstancePage.map(this::convertToFlightInstanceResponse));
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Flight Instance Not Found.")
        );
        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public PageResponse<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                               Long departureAirportId, Long arrivalAirportId, Long flightId,
                                                               LocalDate onDate, Pageable pageable) {
        LocalDateTime start = onDate!=null?onDate.atStartOfDay() : null;
        LocalDateTime end = onDate!=null?onDate.plusDays(1).atStartOfDay() :  null;
        return pageMapper.toPageResponse(
                flightInstanceRepository.findByAirlineId(airlineId,
                        departureAirportId,arrivalAirportId,
                        flightId,start,end,pageable)
                        .map(this::convertToFlightInstanceResponse));

    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {

        AirlineResponse airline = AirlineResponse.builder()
                .id(flightInstance.getAirlineId())
                .build();

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightInstance.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightInstance.getArrivalAirportId())
                .build();

        AircraftResponse aircraftResponse = AircraftResponse.builder()
                .id(flightInstance.getFlight().getAircraftId())
                .build();

        return flightInstanceMapper.toResponse(flightInstance,aircraftResponse,airline,departureAirport,arrivalAirport);
    }
}
