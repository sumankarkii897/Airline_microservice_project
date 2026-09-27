package com.himalayan.payload.request;

import com.himalayan.enums.AirlineStatus;
import lombok.Data;

@Data
public class AirlineStatusUpdateRequest {
    private AirlineStatus airlineStatus;
}
