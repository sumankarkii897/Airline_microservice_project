package com.himalayan.payload.request;

import com.himalayan.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAircraftRequest {
    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 50, message = "Manufacturer length must be less than 50 character")
    private String manufacturer;

    @Positive(message = "Seating capacity must be positive")
    @NotNull(message = "Seating Capacity is required")
    private Integer seatingCapacity;

    @Positive(message = "Economy seats must be positive")
    private Integer economySeats;

    @Positive(message = "Premium Economy seats must be positive")
    private Integer premiumEconomySeats;

    @Positive(message = "Business Seats must be positive")
    private Integer businessSeats;

    @Positive(message = "First Class Seats must be positive")
    private Integer firstClassSeats;

    @NotNull(message = "Year of Manufacture is required." )
    private Integer yearOfManufacture;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    //    @NotNull(message = "Cruising speed kmh is required")
    @Positive(message = "Cruising speed must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Maximum Altitude must be positive")
    private Integer maxAltitudeFt;

    private LocalDateTime registrationDate;
    private LocalDateTime nextMaintenanceDate;

    @NotNull(message = "Status is required")
    private AircraftStatus status;

    @NotNull(message = "Availability status is required")
    private Boolean isAvailable;

    private Long currentAirportId;
}
