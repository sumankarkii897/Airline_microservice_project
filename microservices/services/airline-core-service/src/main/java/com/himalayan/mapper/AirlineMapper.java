package com.himalayan.mapper;

import com.himalayan.embeddable.Support;
import com.himalayan.enums.AirlineStatus;
import com.himalayan.model.Airline;
import com.himalayan.payload.request.AirlineRequest;
import com.himalayan.payload.response.AirlineResponse;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {
   public Airline toEntity(AirlineRequest airlineRequest, Long ownerId) {
        if(airlineRequest == null)  throw new IllegalArgumentException(
                "Airline request cannot be null"
        );
        Airline airline = Airline.builder()
                .iataCode(airlineRequest.getIataCode())
                .icaoCode(airlineRequest.getIcaoCode())
                .name(airlineRequest.getName())
                .alias(airlineRequest.getAlias())
                .logoUrl(airlineRequest.getLogoUrl())
                .website(airlineRequest.getWebsite())
                .alliance(airlineRequest.getAlliance())
                .headquarterCityId(airlineRequest.getHeadquarterCityId())
                .ownerId(ownerId)
                .status(
                        airlineRequest.getStatus() != null
                                ? airlineRequest.getStatus()
                                : AirlineStatus.ACTIVE
                )
                .build();
        if(airlineRequest.getSupportEmail()!=null || airlineRequest.getSupportHours()!=null || airlineRequest.getSupportPhone()!=null){
         airline.setSupport(
                 Support.builder()
                         .email(airlineRequest.getSupportEmail())
                         .phone(airlineRequest.getSupportPhone())
                         .hours(airlineRequest.getSupportHours())
                         .build()
         );

        }
   return airline;
   }

       public AirlineResponse toResponse(Airline airline){
        if(airline == null)  throw new IllegalArgumentException(
                "Airline request cannot be null"
        );
        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .alliance(airline.getAlliance())
                .support(airline.getSupport())
                .status(airline.getStatus())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())



                .build();
    }

    public Airline updateEntity(AirlineRequest airlineRequest, Airline airline) {
        if(airlineRequest == null){
            throw new IllegalArgumentException(
                    "Airline request cannot be null"
            );
        }

        if(airline == null){
            throw new IllegalArgumentException(
                    "Airline cannot be null"
            );
        }
        if(airlineRequest.getIataCode()!=null){
            airline.setIataCode(airlineRequest.getIataCode());
        }
        if(airlineRequest.getIcaoCode()!=null){
            airline.setIcaoCode(airlineRequest.getIcaoCode());
        }
        if(airlineRequest.getName()!=null){
            airline.setName(airlineRequest.getName());
        }
        if(airlineRequest.getAlias()!=null){
            airline.setAlias(airlineRequest.getAlias());
        }
        if(airlineRequest.getLogoUrl()!=null){
            airline.setLogoUrl(airlineRequest.getLogoUrl());
        }
        if(airlineRequest.getWebsite()!=null){
            airline.setWebsite(airlineRequest.getWebsite());
        }
        if(airlineRequest.getAlliance()!=null){
            airline.setAlliance(airlineRequest.getAlliance());
        }
        if(airlineRequest.getHeadquarterCityId()!=null){
            airline.setHeadquarterCityId(airlineRequest.getHeadquarterCityId());
        }
//        if(airlineRequest.getOwnerId()!=null){
//            airline.setOwnerId(airlineRequest.getOwnerId());
//        }
        if(airlineRequest.getSupportEmail()!=null
                || airlineRequest.getSupportPhone()!=null
                || airlineRequest.getSupportHours()!=null){

            if(airline.getSupport()==null){
                airline.setSupport(new Support());
            }


            if(airlineRequest.getSupportEmail()!=null){
                airline.getSupport()
                        .setEmail(airlineRequest.getSupportEmail());
            }

            if(airlineRequest.getSupportPhone()!=null){
                airline.getSupport()
                        .setPhone(airlineRequest.getSupportPhone());
            }

            if(airlineRequest.getSupportHours()!=null){
                airline.getSupport()
                        .setHours(airlineRequest.getSupportHours());
            }
        }
        if(airlineRequest.getStatus()!=null){
            airline.setStatus(airlineRequest.getStatus());
        }
return  airline;
    }

}
