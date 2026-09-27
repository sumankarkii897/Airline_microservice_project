package com.himalayan.payload.response;

import com.himalayan.embeddable.Support;
import com.himalayan.enums.AirlineStatus;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String alias;
    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long ownerId;
    private UserResponse owner;
    private Long updatedById;
    private CityResponse headquartersCity;
    @Embedded
    private Support support;

}
