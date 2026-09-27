package com.himalayan.model;

import com.himalayan.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;


import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flight_instance")
public class FlightInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long airlineId;

    @ManyToOne
    private Flight flight;

    @Column(nullable = false)
    private Long departureAirportId;

    @Column(nullable = false)
    private Long arrivalAirportId;

    @Column(nullable = false)
    private Long scheduleId;

    @Column(nullable = false)
    private LocalDateTime departureDateTime;

    @Column(nullable = false)
    private LocalDateTime arrivalDateTime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private Integer minAdvanceBookingDays;

    private Integer maxAdvanceBookingDays;

    private Boolean isActive=true;

    @Transient
    public String getFormattedDuration() {
        if(departureDateTime == null || arrivalDateTime == null){
            return null;
        }
       Duration duration= Duration.between(departureDateTime, arrivalDateTime);
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    StringBuilder sb = new StringBuilder();
    if(hours>0) sb.append(hours).append("h ");
    if(minutes>0) sb.append(minutes).append("m ");
    return sb.toString().trim();

    }
}
