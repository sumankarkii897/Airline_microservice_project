package com.himalayan.payload.request;

import com.himalayan.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "Flight Id is required.")
    private Long flightId;
    @NotNull(message = "Airport Id is required.")
    private Long airportId;
    @NotNull(message = "Schedule Id is required.")
    private Long scheduleId;
    @NotNull(message = "Departure Airport Id is required.")
    private Long departureAirportId;
    @NotNull(message = "Arrival Airport Id is required.")
    private Long arrivalAirportId;

    @NotNull(message = "Departure date-time is required.")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date-time is required.")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total seats is required.")
    @Positive
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;

}
