package com.himalayan.payload.response;

import com.himalayan.embeddable.Address;
import com.himalayan.embeddable.GeoCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AirportResponse {
    private Long id;
    private String iataCode;
    private String name;
    private Address address;
    private GeoCode geoCode;
    private String timeZone;
    private CityResponse city;
    private String detailedName;

}
