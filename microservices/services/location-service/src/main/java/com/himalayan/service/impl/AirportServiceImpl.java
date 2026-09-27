package com.himalayan.service.impl;

import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.AirportMapper;
import com.himalayan.model.Airport;
import com.himalayan.model.City;
import com.himalayan.payload.request.AirportRequest;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.AirportRepository;
import com.himalayan.repository.CityRepository;
import com.himalayan.service.AirportService;
import com.himalayan.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final PageMapper pageMapper;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) {

        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new AlreadyExistsException("Airport with Iata Code already exists");
        }

        City city = cityRepository.findById(request.getCityId()).orElseThrow(() -> new ResourceNotFoundException("City not found"));
        Airport airport = airportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return airportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) {
        if(id == null){
            return null;
        }
        Airport airport = airportRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Airport with id " + id +" not found"));
        return airportMapper.toResponse(airport);
    }

    @Override
    public PageResponse<AirportResponse> getAirports( Pageable pageable) {

        Page<Airport> airports= airportRepository.findAll(pageable);

        Page<AirportResponse> responses = airports.map(airportMapper::toResponse);
        return pageMapper.toPageResponse(responses);
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) {
        Airport airport = airportRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Airport with id" + id +" not found")
        );

        if (request.getIataCode() != null
        &&
                !airport.getIataCode().equals(request.getIataCode())
                &&
                airportRepository.findByIataCode(request.getIataCode()).isPresent()
        ) {
            throw new AlreadyExistsException("Airport with Iata Code already exists");
        }
        if (request.getCityId() != null) {
            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City not found"));

            airport.setCity(city);
        }


        return airportMapper.toResponse(airportRepository.save(airportMapper.updateAirport(airport,request)));
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Airport with id" + id +" not found")
        );
        airportRepository.delete(airport);
    }

    @Override
    public PageResponse<AirportResponse> getAirportByCityId(long cityId, Pageable pageable) {

        if(!airportRepository.existsByCityId(cityId)) {
                        throw new ResourceNotFoundException("Airport with id " + cityId + " not found");

        }
        Page<Airport> airports = airportRepository.findByCityId(cityId, pageable);


        return pageMapper.toPageResponse(airports.map(airportMapper::toResponse));
    }
}
