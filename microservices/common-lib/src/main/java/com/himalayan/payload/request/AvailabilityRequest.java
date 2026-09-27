package com.himalayan.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRequest {
    @NotNull(message = "available is required.")
    private Boolean available;
}
