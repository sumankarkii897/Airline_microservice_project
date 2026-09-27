package com.himalayan.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private Long id;

    private String name;

    private String cityCode;

    private String countryName;

    private String countryCode;

    private String regionCode;

    private String timeZoneId;
}
