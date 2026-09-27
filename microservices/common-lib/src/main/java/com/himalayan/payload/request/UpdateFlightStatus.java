package com.himalayan.payload.request;

import com.himalayan.enums.FlightStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFlightStatus {
    private FlightStatus status ;
}
