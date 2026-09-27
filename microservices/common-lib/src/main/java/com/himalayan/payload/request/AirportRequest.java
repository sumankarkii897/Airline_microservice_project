package com.himalayan.payload.request;

import com.himalayan.embeddable.Address;
import com.himalayan.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class AirportRequest {

    @NotBlank(message = "IATA Code is required")
    @Size(min = 3,max = 3, message = "IATA Code must be of 3 characters")
    private String iataCode;

    @NotBlank(message = "Name  is required")
    private String name;

    @NotNull(message = "Address is required")
    @Valid
    private Address address;

    @NotNull(message = "GeoCode is required")
    @Valid
    private GeoCode geoCode;

    @NotBlank(message = "TimeZone  is required")
    private String timeZone;

    @NotNull(message = "City Id is required")
    private Long cityId;
}
