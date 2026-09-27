package com.himalayan.service.impl;

import com.himalayan.enums.AircraftStatus;
import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.InvalidRequestException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.AircraftMapper;
import com.himalayan.model.Aircraft;
import com.himalayan.model.Airline;
import com.himalayan.payload.request.AircraftRequest;
import com.himalayan.payload.request.UpdateAircraftRequest;
import com.himalayan.payload.response.AircraftResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.AircraftRepository;
import com.himalayan.repository.AirlineRepository;
import com.himalayan.service.AircraftService;
import com.himalayan.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftMapper aircraftMapper;
    private final PageMapper pageMapper;


    @Override
    @Transactional
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) {
if(aircraftRepository.existsByCode(request.getCode())){
    throw new AlreadyExistsException("Aircraft with code already exists");
}
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Airline not found with id: " + ownerId
                        )
                );

        Aircraft aircraft = aircraftMapper.toEntity(request, airline);
if(aircraft.getSeatingCapacity()<aircraft.getTotalSeats()){
    throw new InvalidRequestException(
            "Total allocated seats cannot exceed seating capacity.");
}
        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        return aircraftMapper.toResponse(savedAircraft);
    }


    @Override
    @Transactional(readOnly = true)
    public PageResponse<AircraftResponse> getAirCrafts(Pageable pageable) {

        Page<Aircraft> aircraftPage =
                aircraftRepository.findAll(pageable);

        Page<AircraftResponse> responsePage =
                aircraftPage.map(aircraftMapper::toResponse);

        return pageMapper.toPageResponse(responsePage);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AircraftResponse> getAirCraftsByAirlineId(Long airlineId) {

        List<Aircraft> aircrafts =
                aircraftRepository.findByAirlineId(airlineId);

        if (aircrafts.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No aircraft found for airline id: " + airlineId
            );
        }

        return aircrafts.stream()
                .map(aircraftMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public AircraftResponse getAirCraftById(Long id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Aircraft not found with id: " + id
                        )
                );

        return aircraftMapper.toResponse(aircraft);
    }


    @Override
    @Transactional
    public AircraftResponse updateAirCraft(
           UpdateAircraftRequest request,
            Long id,
            Long ownerId
    ) {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Airline not found with id: " + ownerId)
        );

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id,airline.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Aircraft not found with id: " + id)
        );

//if(request.getCode()!=null &&
//        !aircraft.getCode().equals(request.getCode()) &&
//aircraftRepository.existsByCode(request.getCode())
//){
//    throw new AlreadyExistsException("Aircraft with code already exists");
//}

        Aircraft updatedAircraft =
                aircraftMapper.updateAircraft(request, aircraft);
        if (updatedAircraft.getTotalSeats() > updatedAircraft.getSeatingCapacity()) {
            throw new InvalidRequestException(
                    "Total allocated seats cannot exceed seating capacity."
            );
        }
        return aircraftMapper.toResponse(updatedAircraft);
    }


    @Override
    @Transactional
    public void deleteAirCraft(Long id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Aircraft not found with id: " + id
                        )
                );

        aircraftRepository.delete(aircraft);
    }


    @Override
    @Transactional
    public void deleteAirCraftByAirlineId(Long airlineId) {

        long deleted = aircraftRepository.deleteByAirlineId(airlineId);
        if(deleted==0){
            throw new ResourceNotFoundException("No aircraft found with  airline id: " + airlineId);
        }
    }


    @Override
    @Transactional
    public AircraftResponse changeStatus(
            Long id,
            AircraftStatus status
    ) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Aircraft not found with id: " + id
                        )
                );
        if (status == null) {
            throw new InvalidRequestException("Status is required.");
        }
        aircraft.setStatus(status);

        return aircraftMapper.toResponse(aircraft);
    }


    @Override
    @Transactional
    public AircraftResponse updateAvailability(
            Long id,
            Boolean available
    ) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Aircraft not found with id: " + id
                        )
                );
        if (available == null) {
            throw new InvalidRequestException("Availability is required.");
        }

        aircraft.setIsAvailable(available);

        return aircraftMapper.toResponse(aircraft);
    }
}