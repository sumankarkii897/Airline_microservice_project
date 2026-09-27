package com.himalayan.service.impl;
import com.himalayan.enums.AirlineStatus;
import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.AirlineMapper;
import com.himalayan.model.Airline;
import com.himalayan.payload.request.AirlineRequest;
import com.himalayan.payload.request.AirlineStatusUpdateRequest;
import com.himalayan.payload.response.AirlineDropdownItem;
import com.himalayan.payload.response.AirlineResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.AirlineRepository;
import com.himalayan.service.AirlineService;
import com.himalayan.util.PageMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;
    private final PageMapper pageMapper;

    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest,
          Long ownerId

    ) {
        if(airlineRepository.existsByIataCode(airlineRequest.getIataCode())){
            throw new AlreadyExistsException(
                    "Airline already exists with this IATA Code"
            );
        }

        if(airlineRepository.existsByIcaoCode(airlineRequest.getIcaoCode())){
            throw new AlreadyExistsException(
                    "Airline already exists with this ICAO Code"
            );
        }
        Airline savedAirline = airlineRepository.save(
                airlineMapper.toEntity(airlineRequest,ownerId)
        );
        return airlineMapper.toResponse(savedAirline);
    }

    @Override
    public PageResponse<AirlineResponse> getAirlines(Pageable pageable) {
        Page<Airline> airlines = airlineRepository.findAll(pageable);

        return  pageMapper.toPageResponse(airlines.map(airlineMapper::toResponse));
    }

    @Override
    public AirlineResponse getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Airline not found with id: " + id)
        );
        return airlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Airline not found with owner id: " + ownerId)
        );
        return airlineMapper.toResponse(airline);
    }

    @Override
    @Transactional
    public AirlineResponse updateAirline(Long id, AirlineRequest airlineRequest) {

       Airline airline = airlineRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("Airline not found with id: " + id)
       );
        if (airlineRequest.getIataCode() != null
                &&
                !airline.getIataCode().equals(airlineRequest.getIataCode())
                &&
                airlineRepository.findByIataCode(airlineRequest.getIataCode()).isPresent()
        ) {
            throw new AlreadyExistsException("Airline with Iata Code already exists");
        }

        airlineMapper.updateEntity(
                airlineRequest,
                airline
        );

        return airlineMapper.toResponse(airline);
    }

    @Override
    public void deleteAirline(Long id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Airline not found with id: " + id)
        );
        airlineRepository.delete(airline);

    }

    @Override
    @Transactional
    public AirlineResponse changeStatus(Long id, AirlineStatusUpdateRequest status) {
        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Airline not found with id: " + id)
        );
        airline.setStatus(status.getAirlineStatus());
        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toResponse(savedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
//      return   airlineRepository.findByStatus(AirlineStatus.ACTIVE).stream()
//                .map(airline -> AirlineDropdownItem.builder()
//                        .id(airline.getId())
//                        .name(airline.getName())
//                        .iataCode(airline.getIataCode())
//                        .icaoCode(airline.getIcaoCode())
//                        .logoUrl(airline.getLogoUrl())
//
//                        .build()).toList();
        return airlineRepository.findDropdownByStatus(
                AirlineStatus.ACTIVE
        );
    }
}
