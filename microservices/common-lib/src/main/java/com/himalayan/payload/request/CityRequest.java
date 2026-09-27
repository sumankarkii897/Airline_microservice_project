package com.himalayan.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {

    @NotBlank(message = "City name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "City code is required")
    @Size(max = 3)
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Size(max = 3)
    private String countryCode;

    @NotBlank(message = "Country name is required")
    @Size(max = 100)
    private String countryName;

    @Size(max = 3)
    private String regionCode;

    @Column(name="time_zone_id", length = 50)
    private String timeZoneId;








}
