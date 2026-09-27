package com.himalayan.payload.request;

import com.himalayan.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftStatusRequest {
    @NotNull(message = "Status is required.")
    private AircraftStatus status;
}
