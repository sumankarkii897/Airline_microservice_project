package com.himalayan.payload.response;

import com.himalayan.enums.AircraftStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;
    private String code;
    private String model;
    private String manufacturer;
    private Integer seatingCapacity;
    private Integer economySeat;
    private Integer premiumEconomySeat;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer yearOfManufacture;
    private Integer cruisingSpeedKmh;
    private Integer rangeKm;
    private Integer maxAltitudeFt;
    private LocalDateTime registrationDate;
    private LocalDateTime nextMaintenanceDate;
    private AircraftStatus status;
    private Boolean isAvailable;

    private Long airlineId;
    private String airlineName;
    private String airlineIataCode;

    private Long currentAirportId;
    private Long currentAirportCity;
    private String currentAirportCode;
    private String currentAirportName;

    private Integer totalSeats;
    private Boolean requiresMaintenance;
    private Boolean isOperational;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
