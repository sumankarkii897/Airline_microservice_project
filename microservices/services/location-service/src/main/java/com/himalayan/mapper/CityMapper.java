package com.himalayan.mapper;

import com.himalayan.model.City;
import com.himalayan.payload.request.CityRequest;
import com.himalayan.payload.response.CityResponse;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public City toEntity(CityRequest request){
        if(request == null){
            return null;
        }
        return City.builder()
                .name(request.getName())
                .cityCode(request.getCityCode())
                .countryName(request.getCountryName())
                .countryCode(request.getCountryCode())
                .regionCode(request.getRegionCode())
                .timeZoneId(request.getTimeZoneId())
                .build();
    }

    public CityResponse toResponse(City city){
        if(city == null){ return null;}
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryName(city.getCountryName())
                .countryCode(city.getCountryCode())
                .regionCode(city.getRegionCode())
                .timeZoneId(city.getTimeZoneId())
                .build();
    }

    public  City updateEntity(City city, CityRequest request){

        if(request.getName() != null){
            city.setName(request.getName().trim());
        }

        if(request.getCityCode() != null){
            city.setCityCode(request.getCityCode().trim());
        }

        if(request.getCountryName() != null){
            city.setCountryName(request.getCountryName().trim());
        }

        if(request.getCountryCode() != null){
            city.setCountryCode(request.getCountryCode().trim());
        }

        if(request.getRegionCode() != null){
            city.setRegionCode(request.getRegionCode().trim());
        }

        if(request.getTimeZoneId() != null){
            city.setTimeZoneId(request.getTimeZoneId().trim());
        }

        return city;
    }
}
