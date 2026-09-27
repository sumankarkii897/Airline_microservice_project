package com.himalayan.mapper;

import com.himalayan.model.Airport;
import com.himalayan.payload.request.AirportRequest;
import com.himalayan.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirportMapper {
    private final CityMapper cityMapper;
    public AirportResponse toResponse(Airport airport){
        if(airport == null){
            return null;
        }
      return AirportResponse.builder()
              .id(airport.getId())
              .name(airport.getName())
              .detailedName(airport.getDetailsName())
              .iataCode(airport.getIataCode())
              .geoCode(airport.getGeoCode())
              .address(airport.getAddress())
              .timeZone(airport.getTimeZone())
              .city(cityMapper.toResponse(airport.getCity()))

              .build();
    }

    public Airport toEntity(AirportRequest airport){
        if(airport == null){
            return null;
        }
        return Airport.builder()
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .geoCode(airport.getGeoCode())
                .address(airport.getAddress())
                .timeZone(airport.getTimeZone())

                .build();
    }

    public Airport updateAirport(Airport airport, AirportRequest request){
        if (airport == null || request == null){
            return null;
        }

        if(request.getIataCode() != null){
            airport.setIataCode(request.getIataCode());
        }
        if(request.getGeoCode() != null){
            airport.setGeoCode(request.getGeoCode());
        }
        if(request.getAddress() != null){
            airport.setAddress(request.getAddress());
        }
        if(request.getTimeZone() != null){
            airport.setTimeZone(request.getTimeZone());
        }
        if(request.getName() != null){
            airport.setName(request.getName());
        }
        return airport;
    }

}
