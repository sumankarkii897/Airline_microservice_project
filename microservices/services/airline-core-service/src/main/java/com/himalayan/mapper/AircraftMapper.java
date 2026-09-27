package com.himalayan.mapper;

import com.himalayan.enums.AirlineStatus;
import com.himalayan.model.Aircraft;
import com.himalayan.model.Airline;
import com.himalayan.payload.request.AircraftRequest;
import com.himalayan.payload.request.UpdateAircraftRequest;
import com.himalayan.payload.response.AircraftResponse;
import com.himalayan.payload.response.AirlineResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AircraftMapper {
    public Aircraft toEntity(AircraftRequest request, Airline airline) {
        if(request == null){
            throw new IllegalArgumentException("request is null");
        }
        return  Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeat(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .yearOfManufacture(request.getYearOfManufacture())
                .rangeKm(request.getRangeKm())
                .status(request.getStatus())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .registrationDate(LocalDateTime.now())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .isAvailable(request.getIsAvailable())
                .currentAirportId(request.getCurrentAirportId())
                .airline(airline)
                .build();

    }

    public AircraftResponse toResponse(Aircraft aircraft){
        if(aircraft == null){
            throw new IllegalArgumentException("aircraft is null");
        }
       AircraftResponse response = AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeat(aircraft.getEconomySeat())
                .premiumEconomySeat(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .rangeKm(aircraft.getRangeKm())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .totalSeats(aircraft.getTotalSeats())
               .requiresMaintenance(aircraft.requiresMaintenance())
               .isOperational(aircraft.isOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
               .currentAirportId(aircraft.getCurrentAirportId())
                .build();
        Airline airline = aircraft.getAirline();
        if(airline != null){
            response.setAirlineId(airline.getId());
            response.setAirlineName(airline.getName());
            response.setAirlineIataCode(airline.getIataCode());
        }
        return response;
    }

    public Aircraft updateAircraft (UpdateAircraftRequest request, Aircraft aircraft){
        if(request == null){
            throw new IllegalArgumentException("request is null");
        }
        if(aircraft == null){
            throw new IllegalArgumentException("aircraft is null");
        }

//       if(request.getCode() != null){
//           aircraft.setCode(request.getCode());
//       }
       if(request.getModel() != null){
           aircraft.setModel(request.getModel());
       }
       if(request.getManufacturer() != null){
           aircraft.setManufacturer(request.getManufacturer());
       }
       if(request.getSeatingCapacity() != null){
           aircraft.setSeatingCapacity(request.getSeatingCapacity());
       }
       if(request.getEconomySeats() != null){
           aircraft.setEconomySeat(request.getEconomySeats());
       }
       if(request.getPremiumEconomySeats() != null){
           aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
       }
       if(request.getBusinessSeats() != null){
           aircraft.setBusinessSeats(request.getBusinessSeats());
       }
       if(request.getFirstClassSeats() != null){
           aircraft.setFirstClassSeats(request.getFirstClassSeats());
       }
       if(request.getYearOfManufacture() != null){
           aircraft.setYearOfManufacture(request.getYearOfManufacture());
       }
       if(request.getRangeKm() != null){
           aircraft.setRangeKm(request.getRangeKm());
       }
       if(request.getMaxAltitudeFt() != null){
           aircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
       }
       if(request.getNextMaintenanceDate() != null){
           aircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
       }
       if(request.getIsAvailable() != null){
           aircraft.setIsAvailable(request.getIsAvailable());
       }
       if(request.getCurrentAirportId() != null){
           aircraft.setCurrentAirportId(request.getCurrentAirportId());
       }
       if(request.getCruisingSpeedKmh() != null){
           aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
       }

return aircraft;
    }

}
